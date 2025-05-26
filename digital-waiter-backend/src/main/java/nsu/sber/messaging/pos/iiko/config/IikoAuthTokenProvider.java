package nsu.sber.messaging.pos.iiko.config;

import lombok.RequiredArgsConstructor;
import nsu.sber.config.RequestContextProvider;
import nsu.sber.messaging.pos.iiko.client.IikoAuthClient;
import nsu.sber.messaging.pos.iiko.dto.AuthRequestDto;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class IikoAuthTokenProvider {
    private static final int TOKEN_LIFESPAN = 15;

    private final IikoAuthClient iikoAuthClient;
    private final RequestContextProvider requestContextProvider;
    private String token;
    private Instant expiresAt = Instant.MIN;

    public synchronized String getToken() {
        if (token == null || Instant.now().isAfter(expiresAt)) {
            token = fetchNewToken();
            expiresAt = Instant.now().plus(Duration.ofMinutes(TOKEN_LIFESPAN - 1));
        }

        return token;
    }

    private String fetchNewToken() {
        return iikoAuthClient.getToken(new AuthRequestDto(requestContextProvider.getApiKey())).getToken();
    }
}
