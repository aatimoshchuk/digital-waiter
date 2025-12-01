package nsu.sber.govno.service.nlu.provider;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.govno.dto.LlmRequest;
import nsu.sber.govno.dto.LlmResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.*;

@Slf4j
@Component
public class GigaChatProvider extends AbstractLlmProvider {

    private static final String AUTH_URL = "https://ngw.devices.sberbank.ru:9443/api/v2/oauth";

    @Value("${gigachat.api.url:https://gigachat.devices.sberbank.ru/api/v1}")
    private String apiUrl;

    @Value("${gigachat.api.key:}")
    private String apiKey;

    @Value("${gigachat.scope:GIGACHAT_API_PERS}")
    private String scope;

    @Value("${gigachat.model:GigaChat}")
    private String model;

    private WebClient webClient;
    private String accessToken;
    private long tokenExpiry;

    @PostConstruct
    public void init() {
        try {
            this.webClient = WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(createUnsafeHttpClient()))
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            if (apiKey != null && !apiKey.isEmpty()) {
                log.info("GigaChat configured. Authenticating...");
                authenticate();
            } else {
                log.warn("GigaChat API key not configured");
            }
        } catch (Exception e) {
            log.error("Failed to initialize GigaChatProvider", e);
        }
    }

    private HttpClient createUnsafeHttpClient() {
        return HttpClient.create()
                .responseTimeout(java.time.Duration.ofSeconds(60))
                .secure(ssl -> {
                    try {
                        ssl.sslContext(
                                SslContextBuilder.forClient()
                                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                                        .build()
                        );
                    } catch (SSLException e) {
                        throw new RuntimeException("Failed to create SSL context", e);
                    }
                });
    }

    private void authenticate() {
        try {
            log.info("Requesting access token from GigaChat...");

            Map<String, Object> response = webClient.post()
                    .uri(AUTH_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + apiKey)
                    .header("RqUID", UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue("scope=" + scope)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block(java.time.Duration.ofSeconds(60));

            if (response != null && response.containsKey("access_token")) {
                this.accessToken = (String) response.get("access_token");
                long expiresIn = (long) response.getOrDefault("expires_at", 1800);
                this.tokenExpiry = expiresIn - 60000;
                long secondsUntilExpiry = (expiresIn - System.currentTimeMillis()) / 1000;
                log.info("✓ GigaChat authenticated successfully. Token expires in {} seconds", secondsUntilExpiry);
            } else {
                log.error("Authentication response missing access_token");
            }
        } catch (Exception e) {
            log.error("❌ GigaChat authentication failed: {}", e.getMessage());
            log.debug("Full error:", e);
        }
    }

    @Override
    protected LlmResponse sendLlmRequest(LlmRequest request) throws Exception {
        // Проверяем и обновляем токен если нужно
        if (accessToken == null || accessToken.isEmpty() || System.currentTimeMillis() > tokenExpiry) {
            log.info("Token expired or missing, re-authenticating...");
            authenticate();
        }

        if (accessToken == null) {
            return LlmResponse.error("Authentication failed");
        }

        try {
            Map<String, Object> body = buildRequestBody(request);

            log.debug("Sending request to GigaChat: {}", body);

            Map<String, Object> response = webClient.post()
                    .uri(apiUrl + "/chat/completions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block(java.time.Duration.ofSeconds(60));

            return parseResponse(response);
        } catch (Exception e) {
            log.error("Failed to send request to GigaChat", e);
            return LlmResponse.error("Request failed: " + e.getMessage());
        }
    }

    private Map<String, Object> buildRequestBody(LlmRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", model);

        List<Map<String, String>> messages = new ArrayList<>();

        // Добавляем системный промпт если есть
        if (request.getPrompt() != null && !request.getPrompt().isEmpty()) {
            messages.add(Map.of("role", "system", "content", request.getPrompt()));
        }

        // Добавляем сообщение пользователя
        messages.add(Map.of("role", "user", "content", request.getMessage()));

        body.put("messages", messages);

        return body;
    }

    private LlmResponse parseResponse(Map<String, Object> response) {
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> choice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                String content = (String) message.get("content");

                log.debug("Received response from GigaChat: {} chars", content.length());

                return LlmResponse.builder()
                        .content(content)
                        .success(true)
                        .build();
            }
        } catch (Exception e) {
            log.error("Failed to parse GigaChat response", e);
        }
        return LlmResponse.error("Failed to parse response");
    }

    @Override
    public boolean isAvailable() {
        return accessToken != null && System.currentTimeMillis() < tokenExpiry;
    }
}