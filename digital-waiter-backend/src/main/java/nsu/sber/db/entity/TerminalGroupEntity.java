package nsu.sber.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "terminal_group")
public class TerminalGroupEntity {

    @Id
    @SequenceGenerator(name = "terminal_group_id_generator", sequenceName = "terminal_group_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "terminal_group_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "pos_terminal_group_id")
    private String posTerminalGroupId;

    @Column(name = "organization_id")
    private Integer organizationId;

}
