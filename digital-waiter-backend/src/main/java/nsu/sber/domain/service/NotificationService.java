package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.model.entity.Notification;
import nsu.sber.domain.model.entity.NotificationStatus;
import nsu.sber.domain.model.notifications.AckNotificationRequest;
import nsu.sber.domain.port.repository.jpa.NotificationRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    @Value("${notifications.retry.retry-seconds}")
    private long retrySeconds;

    @Value("${notifications.expiration.expiration-minutes}")
    private long expirationMinutes;

    private final NotificationRepositoryPort notificationRepository;

    private final TerminalGroupService terminalGroupService;
    private final RestaurantTableService restaurantTableService;

    public void sendCallWaiterNotification() {
        Notification notification = Notification
                .builder()
                .posTerminalGroupId(terminalGroupService.getCurrentTerminalGroup().getPosTerminalGroupId())
                .text("Вызов официанта: " + restaurantTableService.getCurrentRestaurantTable().getName())
                .status(NotificationStatus.NEW)
                .createdAt(OffsetDateTime.now())
                .build();

        notificationRepository.save(notification);
    }

    @Transactional(transactionManager = "transactionManager")
    public List<Notification> pullNotificationsByTerminalGroupId(String terminalGroupId) {
        List<Notification> notifications = notificationRepository.findByPosTerminalGroupIdAndStatus(
                terminalGroupId,
                NotificationStatus.NEW
        );

        OffsetDateTime pulledAt = OffsetDateTime.now();

        for (Notification notification : notifications) {
            notification.setPullToken(UUID.randomUUID().toString());
            notification.setPulledAt(pulledAt);
            notification.setStatus(NotificationStatus.IN_PROGRESS);
        }

        return notificationRepository.save(notifications);
    }

    @Transactional(transactionManager = "transactionManager")
    public void ackNotifications(AckNotificationRequest request) {
        for (AckNotificationRequest.AckItem item : request.getNotifications()) {
            var optionalNotification = notificationRepository.findByIdAndPullToken(item.getId(), item.getPullToken());

            if (optionalNotification.isEmpty()) {
                continue;
            }

            Notification notification = optionalNotification.get();

            if (notification.getStatus() == NotificationStatus.IN_PROGRESS) {
                notification.setStatus(NotificationStatus.ACKED);
                notificationRepository.save(notification);
            }
        }
    }

    @Transactional(transactionManager = "transactionManager")
    public void requeueTimedOutNotifications() {
        int updated = notificationRepository.requeueTimedOut(OffsetDateTime.now().minusSeconds(retrySeconds));

        if (updated > 0) {
            log.info("Requeued {} timed out notifications", updated);
        }
    }

    @Transactional(transactionManager = "transactionManager")
    public void expireNotifications() {
        int updated = notificationRepository.markExpired(OffsetDateTime.now().minusMinutes(expirationMinutes));

        if (updated > 0) {
            log.info("Marked {} notifications as EXPIRED", updated);
        }
    }
}
