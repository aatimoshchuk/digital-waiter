package nsu.sber.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationExpirationScheduler {
    private final NotificationService notificationService;

    @Scheduled(fixedDelayString = "${notifications.expiration.fixed-delay-ms}")
    public void run() {
        notificationService.expireNotifications();
    }
}
