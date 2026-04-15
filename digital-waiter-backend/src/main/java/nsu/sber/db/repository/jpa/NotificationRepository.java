package nsu.sber.db.repository.jpa;

import nsu.sber.db.entity.NotificationEntity;
import nsu.sber.db.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByPosTerminalGroupIdAndStatus(
            String posTerminalGroupId,
            NotificationStatus status
    );

    Optional<NotificationEntity> findByIdAndPullToken(Long id, String pullToken);

    @Modifying
    @Query("""
        update NotificationEntity n
        set n.status = 'EXPIRED'
        where n.status = 'NEW' or n.status = 'IN_PROGRESS'
          and n.createdAt >= :threshold
    """)
    int markExpired(@Param("threshold") OffsetDateTime threshold);

    @Modifying
    @Query("""
        update NotificationEntity n
        set n.status = 'NEW',
            n.pulledAt = null,
            n.pullToken = null
        where n.status = 'IN_PROGRESS'
          and n.pulledAt is not null
          and n.pulledAt >= :threshold
    """)
    int requeueTimedOut(@Param("threshold") OffsetDateTime threshold);
}
