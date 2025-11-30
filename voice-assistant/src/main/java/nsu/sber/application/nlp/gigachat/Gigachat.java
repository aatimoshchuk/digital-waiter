package nsu.sber.application.nlp.gigachat;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;



@Slf4j
@Service
public class Gigachat {

    private static final String AUTH_URL = "https://ngw.devices.sberbank.ru:9443/api/v2/oauth";
    private static final String API_URL = "https://gigachat.devices.sberbank.ru/api/v1/chat/completions";

    private final WebClient webClient;

    public Gigachat() {
        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(createUnsafeHttpClient()))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String sendPrompt(String prompt) {
        String token = requestAccessToken();

        Map<String, Object> response = webClient.post()
                .uri(API_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(Map.of(
                        "model", "GigaChat",
                        "messages", List.of(Map.of("role", "user", "content", prompt))
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return extractText(response);
    }

    private String requestAccessToken() {
        String authKey = System.getenv("GIGACHAT_AUTH_KEY");

        Map<String, Object> body = webClient.post()
                .uri(AUTH_URL)
                .header("Authorization", "Basic " + authKey)
                .header("RqUID", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("scope=GIGACHAT_API_PERS")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return body.get("access_token").toString();
    }

    private HttpClient createUnsafeHttpClient() {
            return HttpClient.create().secure(ssl -> {
                try {
                    ssl.sslContext(
                            SslContextBuilder.forClient()
                                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                                    .build()
                    );
                } catch (SSLException e) {
                    throw new RuntimeException(e);
                }
            });

    }

    @SuppressWarnings("unchecked")
    private String extractText(Map<String, Object> response) {
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return message.get("content").toString();
        } catch (Exception e) {
            throw new RuntimeException("Invalid GigaChat response format");
        }
    }
}
