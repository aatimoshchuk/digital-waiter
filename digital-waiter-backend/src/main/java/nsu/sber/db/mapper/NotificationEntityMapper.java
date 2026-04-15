package nsu.sber.db.mapper;

import nsu.sber.db.entity.NotificationEntity;
import nsu.sber.db.entity.NotificationStatus;
import nsu.sber.domain.model.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = TerminalGroupEntityMapper.class)
public interface NotificationEntityMapper {
    NotificationEntity notificationToEntity(Notification notification);

    Notification entityToNotification(NotificationEntity notificationEntity);

    List<Notification> entitiesToNotifications(List<NotificationEntity> entities);

    List<NotificationEntity> notificationsToEntities(List<Notification> notifications);

    NotificationStatus statusToEntityStatus(nsu.sber.domain.model.entity.NotificationStatus status);
}
