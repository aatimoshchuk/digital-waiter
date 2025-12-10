package nsu.sber.domain.model.restaurant_table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRestaurantTableResponse {

    private String tableName;

    private String login;

    private String password;

}
