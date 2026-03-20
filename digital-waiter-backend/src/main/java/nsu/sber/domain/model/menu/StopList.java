package nsu.sber.domain.model.menu;

import lombok.Data;

import java.util.List;

@Data
public class StopList {

    private List<TerminalGroupStopList> terminalGroupStopLists;

    @Data
    public static class TerminalGroupStopList {
        private String organizationId;
        private List<TerminalGroupStopListItem> items;
    }

    @Data
    public static class TerminalGroupStopListItem {
        private String terminalGroupId;
        private List<StopListItem> items;
    }

    @Data
    public static class StopListItem {
        private Double balance;
        private String productId;
    }

}
