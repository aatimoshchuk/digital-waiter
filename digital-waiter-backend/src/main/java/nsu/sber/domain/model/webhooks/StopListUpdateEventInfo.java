package nsu.sber.domain.model.webhooks;

import lombok.Data;

import java.util.List;

@Data
public class StopListUpdateEventInfo {
    private List<TerminalGroupsStopListsUpdate> terminalGroupsStopListsUpdates;

    @Data
    public static class TerminalGroupsStopListsUpdate {
        private String id;
        private Boolean isFull;
    }
}
