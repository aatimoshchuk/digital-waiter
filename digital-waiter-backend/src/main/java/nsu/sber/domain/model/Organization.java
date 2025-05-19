package nsu.sber.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Organization {

    private Integer id;

    private String apiKey;

    private String posOrganizationId;

}
