package nsu.sber.web.controller;

import lombok.Builder;
import lombok.Data;
import nsu.sber.web.dto.NotificationDto;

import java.util.List;

@Data
@Builder
public class GetNotificationsResponseDto {
    private List<NotificationDto> notifications;
}
