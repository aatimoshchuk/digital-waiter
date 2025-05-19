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
@Table(name = "organization")
public class OrganizationEntity {

    @Id
    @SequenceGenerator(name = "organization_id_generator", sequenceName = "organization_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "organization_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "pos_org_id")
    private String posOrganizationId;
}
