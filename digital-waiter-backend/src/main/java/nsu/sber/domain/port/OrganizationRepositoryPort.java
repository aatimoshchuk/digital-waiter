package nsu.sber.domain.port;

import nsu.sber.domain.model.Organization;

public interface OrganizationRepositoryPort {

    Organization findById(int id);

}
