package nsu.sber.govno.service.stt;

import org.springframework.web.multipart.MultipartFile;

public interface SttService {

    String recognizeFile(MultipartFile audioFile);
}
