package nsu.sber.domain.model.restaurant_table;

import lombok.Data;


@Data
public class CreateRestaurantTableRequest {

    private String name;

    private String posTableId;

    private Integer terminalGroupId;

}
