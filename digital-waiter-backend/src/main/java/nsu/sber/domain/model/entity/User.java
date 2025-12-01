package nsu.sber.domain.model.entity;

import lombok.Data;

@Data
public class User {

    private Integer id;

    private String login;

    private String passwordHash;

    private RoleType role;

    private RestaurantTable restaurantTable;

}
