package nsu.sber.domain.port.repository.jpa;

import nsu.sber.domain.model.entity.Notification;
import nsu.sber.domain.model.entity.NotificationStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryPort {
    Notification save(Notification notification);

    List<Notification> save(List<Notification> notifications);

    List<Notification> findByPosTerminalGroupIdAndStatus(String posTerminalGroupId, NotificationStatus status);

    Optional<Notification> findByIdAndPullToken(Long id, String pullToken);

    int markExpired(OffsetDateTime threshold);

    int requeueTimedOut(OffsetDateTime threshold);
}
