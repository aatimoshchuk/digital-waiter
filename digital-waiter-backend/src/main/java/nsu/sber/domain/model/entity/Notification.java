package nsu.sber.domain.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class Notification {
    private Long id;

    private String posTerminalGroupId;

    private String text;

    private NotificationStatus status;

    private OffsetDateTime createdAt;

    private OffsetDateTime pulledAt;

    private String pullToken;
}
