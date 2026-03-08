package nsu.sber.voiceassistant.service.stt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class VoskSttService implements SttService {
    private String voskHttpUrl;
    private final RestTemplate restTemplate;

    public VoskSttService(
            RestTemplate restTemplate,
            @Value("${vosk.server.http.url:http://localhost:2701}") String voskHttpUrl) {
        this.restTemplate = restTemplate;
        this.voskHttpUrl = voskHttpUrl;
    }
    @Override
    public String recognizeFile(MultipartFile audioFile) {
        try {
            byte[] audioData = audioFile.getBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.set("X-Sample-Rate", "16000");
            HttpEntity<byte[]> request = new HttpEntity<>(audioData, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    voskHttpUrl + "/recognize",
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();
            if (response.getStatusCode() == HttpStatus.OK && body != null) {
                return (String) body.get("text");
            }
            return "Not Recognized";

        } catch (IOException e) {
            throw new RuntimeException("Failed to read audio file", e);
        } catch (Exception e) {
            throw new RuntimeException("STT failed", e);
        }
    }
}

