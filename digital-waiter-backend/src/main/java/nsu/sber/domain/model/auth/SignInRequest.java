package nsu.sber.domain.model.auth;

import lombok.Data;

@Data
public class SignInRequest {

    private String login;

    private String password;

}
