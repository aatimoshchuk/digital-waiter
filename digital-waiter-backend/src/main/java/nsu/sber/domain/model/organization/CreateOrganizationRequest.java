package nsu.sber.domain.model.organization;

import lombok.Data;

@Data
public class CreateOrganizationRequest {

    private String name;

    private String posOrganizationId;

    private String apiKey;

}
