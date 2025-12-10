package nsu.sber.domain.model.restaurant_table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantTableResponse {

    private Integer id;

    private String name;

    private String posTableId;

    private Integer terminalGroupId;

    private String login;

}
