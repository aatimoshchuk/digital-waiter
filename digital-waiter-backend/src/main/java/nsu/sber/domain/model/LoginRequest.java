package nsu.sber.domain.model;

import lombok.Data;

@Data
public class LoginRequest {

    private String login;

    private String password;

}
