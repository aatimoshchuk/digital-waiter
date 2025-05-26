package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.model.Organization;
import nsu.sber.domain.port.OrganizationRepositoryPort;
import nsu.sber.exception.ErrorType;
import nsu.sber.exception.ServiceException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepositoryPort organizationRepository;

    public Organization getOrganizationById(int id) {
        Organization organization = organizationRepository.findById(id);

        if (organization == null) {
            String errorMessage = "Организация с id %d не найдена".formatted(id);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.ORGANIZATION_NOT_FOUND);
        }

        return organization;
    }
}
