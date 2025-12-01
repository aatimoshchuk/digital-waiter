package nsu.sber.govno.service.stt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VoskSttService implements SttService {
    @Value("${vosk.server.http.url:http://localhost:2701}")
    private String voskHttpUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public String recognizeFile(MultipartFile audioFile) {
        try {
            byte[] audioData = audioFile.getBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.set("X-Sample-Rate", "16000");
            HttpEntity<byte[]> request = new HttpEntity<>(audioData, headers);

            String url = voskHttpUrl + "/recognize";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();
                String text = (String) body.get("text");
                return text;
            }
            return "Not Recognized";

        } catch (IOException e) {
            throw new RuntimeException("Failed to read audio file", e);
        } catch (Exception e) {
            throw new RuntimeException("STT failed", e);
        }
    }
}

