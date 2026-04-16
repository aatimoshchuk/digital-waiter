package nsu.sber.web.dto;

import lombok.Builder;
import lombok.Data;
import nsu.sber.web.dto.NotificationDto;

import java.util.List;

@Data
@Builder
public class PullNotificationsResponseDto {
    private List<NotificationDto> notifications;
}
