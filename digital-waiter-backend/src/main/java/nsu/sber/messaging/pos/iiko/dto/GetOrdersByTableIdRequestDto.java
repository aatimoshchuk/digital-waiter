package nsu.sber.messaging.pos.iiko.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetOrdersByTableIdRequestDto {
    private List<String> organizationIds;
    private List<String> tableIds;
    private List<String> statuses;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dateFrom;
}
