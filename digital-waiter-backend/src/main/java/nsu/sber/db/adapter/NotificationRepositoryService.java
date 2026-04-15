package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.entity.NotificationEntity;
import nsu.sber.db.mapper.NotificationEntityMapper;
import nsu.sber.db.repository.jpa.NotificationRepository;
import nsu.sber.domain.model.entity.Notification;
import nsu.sber.domain.model.entity.NotificationStatus;
import nsu.sber.domain.port.repository.jpa.NotificationRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationRepositoryService implements NotificationRepositoryPort {
    private final NotificationRepository notificationRepository;
    private final NotificationEntityMapper notificationEntityMapper;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity notificationToSave = notificationEntityMapper.notificationToEntity(notification);
        return notificationEntityMapper.entityToNotification(notificationRepository.save(notificationToSave));
    }

    @Override
    public List<Notification> save(List<Notification> notifications) {
        List<NotificationEntity> notificationsToSave = notificationEntityMapper.notificationsToEntities(notifications);
        return notificationEntityMapper.entitiesToNotifications(notificationRepository.saveAll(notificationsToSave));
    }

    @Override
    public List<Notification> findByPosTerminalGroupIdAndStatus(String posTerminalGroupId, NotificationStatus status) {
        return notificationEntityMapper.entitiesToNotifications(notificationRepository.findByPosTerminalGroupIdAndStatus(
                posTerminalGroupId,
                notificationEntityMapper.statusToEntityStatus(status)
        ));
    }

    @Override
    public Optional<Notification> findByIdAndPullToken(Long id, String pullToken) {
        return notificationRepository.findByIdAndPullToken(id, pullToken)
                .map(notificationEntityMapper::entityToNotification);
    }

    @Override
    public int markExpired(OffsetDateTime threshold) {
        return notificationRepository.markExpired(threshold);
    }

    @Override
    public int requeueTimedOut(OffsetDateTime threshold) {
        return notificationRepository.requeueTimedOut(threshold);
    }
}
