package nsu.sber.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurant_tables")
public class RestaurantTableEntity {

    @Id
    @SequenceGenerator(
            name = "restaurant_tables_id_generator",
            sequenceName = "restaurant_table_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(generator = "restaurant_tables_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "pos_table_id")
    private String posTableId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal_group_id", nullable = false)
    private TerminalGroupEntity terminalGroup;

}
