package nsu.sber.domain.port.crypto;

public interface ApiKeyCryptoPort {
    String encrypt(String plaintext);
    String decrypt(String ciphertext);
}
