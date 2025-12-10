package nsu.sber.domain.model.restaurant_table;

import lombok.Data;

@Data
public class UpdateRestaurantTableRequest {

    private String name;

    private String posTableId;

    private Integer terminalGroupId;

    private String login;

    private String password;
}
