package nsu.sber.service.nlu.provider;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.dto.LlmRequest;
import nsu.sber.dto.LlmResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GigaChatProvider extends AbstractLlmProvider {

    @Value("${gigachat.api.url:https://gigachat.devices.sberbank.ru/api/v1}")
    private String apiUrl;

    @Value("${gigachat.api.key:}")
    private String apiKey;

    @Value("${gigachat.api.key:}")
    private String scope;

    @Value("${gigachat.model:GigaChat}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();
    private String accessToken;
    private long tokenExpiry;

    @PostConstruct
    public void init() {
        if (apiKey == null || apiKey.isEmpty()) {
            authenticate();
        }
    }

    private void authenticate() {
        try {
            String authUrl = "https://ngw.devices.sberbank.ru:9443/api/v2/oauth";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(apiKey, "");

            HttpEntity<String> request = new HttpEntity<>("scope=" + scope, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, request, Map.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();
                this.accessToken = (String) body.get("access_token");
                int expiresIn = (int) body.getOrDefault("expires_in", 3600);
                this.tokenExpiry = System.currentTimeMillis() + expiresIn * 1000L;
                log.info("GigaChat authenticated successfully");
            }
        } catch (Exception e) {
            log.error("GigaChat authentication failed", e);
        }
    }

    @Override
    protected LlmResponse sendLlmRequest(LlmRequest request) throws Exception {
        if (accessToken == null || accessToken.isEmpty() || System.currentTimeMillis() > this.tokenExpiry) {
            authenticate();
        }

        Map<String, Object> body = buildRequestBody(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, Object>> httpRequest = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                apiUrl + "/char/completions",
                httpRequest,
                Map.class
        );
        return parseResponse(response.getBody());
    }

    private Map<String, Object> buildRequestBody(LlmRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("message", request.getMessage());
        body.put("prompt", request.getPrompt());
        return body;
    }

    private LlmResponse parseResponse(Map<String, Object> response) {
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> choice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                String content = (String) message.get("content");
                return LlmResponse.builder()
                        .content(content)
                        .success(true)
                        .build();
            }
        } catch (Exception e) {
            log.error("GigaChat parseResponse failed", e);
        }
        return LlmResponse.error("GigaChat parseResponse failed");
    }

    @Override
    public boolean isAvailable() {
        return accessToken != null && System.currentTimeMillis() < this.tokenExpiry;
    }
}
