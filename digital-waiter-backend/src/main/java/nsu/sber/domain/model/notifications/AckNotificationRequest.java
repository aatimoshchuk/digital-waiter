package nsu.sber.domain.model.notifications;

import lombok.Data;

import java.util.List;

@Data
public class AckNotificationRequest {
    private List<AckItem> notifications;

    @Data
    public static class AckItem {
        private Long id;
        private String pullToken;
    }
}
