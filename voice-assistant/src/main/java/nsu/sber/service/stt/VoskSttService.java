package nsu.sber.service.stt;

import org.springframework.web.multipart.MultipartFile;

public class VoskSttService implements SttService {
    @Override
    public String recognizeFile(MultipartFile audioFile) {
        return "Mock: добавить пиццу";
    }
}
