package nsu.sber.infrastructure.crypto;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.port.crypto.ApiKeyCryptoPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiKeyCryptoAdapter implements ApiKeyCryptoPort {
    private final ApiKeyCryptoService apiKeyCryptoService;

    @Override
    public String encrypt(String plaintext) {
        return apiKeyCryptoService.encrypt(plaintext);
    }

    @Override
    public String decrypt(String ciphertext) {
        return apiKeyCryptoService.decrypt(ciphertext);
    }
}
