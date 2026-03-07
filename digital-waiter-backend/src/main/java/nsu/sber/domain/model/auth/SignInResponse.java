package nsu.sber.domain.model.auth;

import lombok.Builder;
import lombok.Data;
import nsu.sber.domain.model.entity.RoleType;

@Data
@Builder
public class SignInResponse {

    private JwtAuthentication jwtAuthentication;

    private RoleType role;

}
