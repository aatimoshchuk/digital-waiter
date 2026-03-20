package nsu.sber.messaging.pos.iiko.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StopListResponseDto {

    private List<TerminalGroupStopList> terminalGroupStopLists;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TerminalGroupStopList {
        private String organizationId;
        private List<TerminalGroupStopListItem> items;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TerminalGroupStopListItem {
        private String terminalGroupId;
        private List<StopListItem> items;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StopListItem {
        private Double balance;
        private String productId;
    }

}
