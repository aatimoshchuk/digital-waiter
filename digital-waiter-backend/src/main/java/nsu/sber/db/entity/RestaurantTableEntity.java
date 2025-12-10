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
@Table(name = "restaurant_tables", schema = "dw")
public class RestaurantTableEntity {

    @Id
    @SequenceGenerator(
            name = "restaurant_tables_id_generator",
            sequenceName = "dw.restaurant_tables_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(generator = "restaurant_tables_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "pos_table_id")
    private String posTableId;

    @Column(name = "terminal_group_id")
    private Integer terminalGroupId;

}
