package nsu.sber.domain.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Integer id;

    private String login;

    private String passwordHash;

    private RoleType role;

    private Integer restaurantTableId;

}
