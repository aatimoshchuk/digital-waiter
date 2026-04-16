package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.NotificationService;
import nsu.sber.web.dto.AckNotificationRequestDto;
import nsu.sber.web.dto.NotificationDto;
import nsu.sber.web.mapper.NotificationDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@Tag(name = "Notification Controller")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationDtoMapper notificationDtoMapper;

    @PostMapping("/call-waiter")
    @Operation(
            summary = "Call a waiter",
            description = "Sends a notification to iikoFront that the waiter needs to come to the table"
    )
    public void callWaiter() {
        notificationService.sendCallWaiterNotification();
    }

    @GetMapping("/plugin")
    @Operation(
            summary = "Get notifications related to a specific terminal group",
            description = "Returns notifications associated with terminalGroupId (only for use by the iikoFront plugin)"
    )
    public GetNotificationsResponseDto getNotifications(@RequestBody @Valid GetNotificationsRequestDto dto) {
        List<NotificationDto> notifications = notificationDtoMapper.notificationsToDtoList(
                notificationService.getNotificationsByTerminalGroupId(dto.getTerminalGroupId())
        );

        return GetNotificationsResponseDto
                .builder()
                .notifications(notifications)
                .build();
    }

    @PostMapping("/plugin/ack")
    @Operation(
            summary = "Acknowledge notifications by its ids and pullTokens",
            description = "Marks notifications as 'ACKED'"
    )
    public void ackNotifications(@RequestBody @Valid AckNotificationRequestDto request) {
        notificationService.ackNotifications(notificationDtoMapper.dtoToAckNotificationRequest(request));
    }
}
