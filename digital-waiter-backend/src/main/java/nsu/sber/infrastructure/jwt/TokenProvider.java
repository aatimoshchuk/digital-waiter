package nsu.sber.infrastructure.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProvider {
    String generateToken(UserDetails userDetails, int tokenDurationInMinutes);
    String extractLogin(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
