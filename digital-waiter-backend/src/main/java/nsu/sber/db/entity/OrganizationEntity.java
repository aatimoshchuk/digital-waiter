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
@Table(name = "organizations", schema = "dw")
public class OrganizationEntity {

    @Id
    @SequenceGenerator(
            name = "organizations_id_generator",
            sequenceName = "dw.organizations_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(generator = "organizations_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "api_key_encrypted")
    private String apiKeyEncrypted;

    @Column(name = "pos_org_id")
    private String posOrganizationId;

}
