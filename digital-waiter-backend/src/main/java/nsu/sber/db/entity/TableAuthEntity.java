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
@Table(name = "table_auth")
public class TableAuthEntity {

    @Id
    @SequenceGenerator(name = "table_auth_id_generator", sequenceName = "table_auth_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "table_auth_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

}
