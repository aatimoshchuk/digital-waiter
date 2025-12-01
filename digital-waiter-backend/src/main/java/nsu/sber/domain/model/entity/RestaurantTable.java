package nsu.sber.domain.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantTable {

    private Integer id;

    private String number;

    private String posTableId;

    private Integer terminalGroupId;

}
