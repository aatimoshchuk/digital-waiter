package nsu.sber.infrastructure.security;

import nsu.sber.domain.port.PasswordGeneratorPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class BasicPasswordGenerator implements PasswordGeneratorPort {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALL = UPPER + LOWER + DIGITS;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Value("${password.length}")
    private Integer length;

    public String generate() {
        List<Character> pwd = new ArrayList<>();

        pwd.add(randomChar(UPPER));
        pwd.add(randomChar(LOWER));
        pwd.add(randomChar(DIGITS));

        while (pwd.size() < length) {
            pwd.add(randomChar(ALL));
        }

        Collections.shuffle(pwd, RANDOM);

        StringBuilder sb = new StringBuilder(length);
        for (char c : pwd) sb.append(c);

        return sb.toString();
    }

    private static char randomChar(String src) {
        return src.charAt(RANDOM.nextInt(src.length()));
    }
}