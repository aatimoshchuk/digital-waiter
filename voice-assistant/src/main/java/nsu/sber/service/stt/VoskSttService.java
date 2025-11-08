package nsu.sber.service.stt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VoskSttService implements SttService {
    @Override
    public String recognizeFile(MultipartFile audioFile) {
        return "Mock: добавить пиццу";
    }
}
