package nsu.sber.web.mapper;

import nsu.sber.domain.model.entity.Notification;
import nsu.sber.domain.model.notifications.AckNotificationRequest;
import nsu.sber.web.dto.AckNotificationRequestDto;
import nsu.sber.web.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface NotificationDtoMapper {
    List<NotificationDto> notificationsToDtoList(List<Notification> notifications);

    AckNotificationRequest dtoToAckNotificationRequest(AckNotificationRequestDto dto);
}
