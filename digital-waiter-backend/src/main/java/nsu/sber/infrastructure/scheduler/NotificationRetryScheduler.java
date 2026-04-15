package nsu.sber.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationRetryScheduler {
    private final NotificationService notificationService;

    @Scheduled(fixedDelayString = "${notifications.retry.fixed-delay-ms}")
    public void run() {
        notificationService.requeueTimedOutNotifications();
    }
}
