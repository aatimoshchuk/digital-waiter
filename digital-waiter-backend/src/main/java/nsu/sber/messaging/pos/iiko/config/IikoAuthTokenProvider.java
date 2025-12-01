package nsu.sber.messaging.pos.iiko.config;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.service.OrganizationService;
import nsu.sber.infrastructure.crypto.ApiKeyCryptoService;
import nsu.sber.messaging.pos.iiko.client.IikoAuthClient;
import nsu.sber.messaging.pos.iiko.dto.AuthRequestDto;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class IikoAuthTokenProvider {
    private static final int TOKEN_LIFESPAN_MINUTES = 15;

    private final IikoAuthClient iikoAuthClient;

    private final OrganizationService organizationService;
    private final ApiKeyCryptoService apiKeyCryptoService;

    private final Map<Integer, TokenEntry> tokens = new ConcurrentHashMap<>();

    public String getToken() {
        Organization organization = organizationService.getCurrentOrganization();

        TokenEntry entry = tokens.compute(organization.getId(), (id, old) -> {
            if (old == null || Instant.now().isAfter(old.expiresAt())) {
                String apiKey = apiKeyCryptoService.decrypt(organization.getApiKeyEncrypted());
                String newToken = iikoAuthClient
                        .getToken(new AuthRequestDto(apiKey))
                        .getToken();

                return new TokenEntry(
                        newToken,
                        Instant.now().plus(Duration.ofMinutes(TOKEN_LIFESPAN_MINUTES - 1))
                );
            }
            return old;
        });

        return entry.token;
    }

    private record TokenEntry(String token, Instant expiresAt) {}
}
