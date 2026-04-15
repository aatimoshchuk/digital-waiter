package nsu.sber.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "notifications", schema = "dw")
public class NotificationEntity {
    @Id
    @SequenceGenerator(
            name = "notifications_id_generator",
            sequenceName = "dw.notifications_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(generator = "notifications_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "pos_terminal_group_id")
    private String posTerminalGroupId;

    @Column(name = "text")
    private String text;

    @Column(name = "status")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "pulled_at")
    private OffsetDateTime pulledAt;

    @Column(name = "pull_token")
    private String pullToken;
}
