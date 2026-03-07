package nsu.sber.domain.model.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthentication {

    private String accessToken;

    private String refreshToken;

}
