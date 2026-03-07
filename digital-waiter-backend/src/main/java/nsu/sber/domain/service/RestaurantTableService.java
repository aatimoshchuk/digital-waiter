package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.mapper.RestaurantTableMapper;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.entity.User;
import nsu.sber.domain.model.restaurant_table.CreateRestaurantTableRequest;
import nsu.sber.domain.model.restaurant_table.CreateRestaurantTableResponse;
import nsu.sber.domain.model.restaurant_table.RestaurantTableResponse;
import nsu.sber.domain.model.restaurant_table.UpdateRestaurantTableRequest;
import nsu.sber.domain.port.repository.jpa.RestaurantTableRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestaurantTableService {
    private final RestaurantTableRepositoryPort restaurantTableRepository;

    private final RestaurantTableMapper restaurantTableMapper;

    private final UserService userService;
    private final TerminalGroupService terminalGroupService;
    private final OrganizationService organizationService;

    public RestaurantTable getRestaurantTable(Integer id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.RestaurantTableWithThisIdNotFoundException(id));
    }

    public RestaurantTable getCurrentRestaurantTable() {
        return getRestaurantTable(userService.getCurrentUser().getRestaurantTableId());
    }

    public CreateRestaurantTableResponse createRestaurantTable(CreateRestaurantTableRequest request) {
        Integer terminalGroupId = request.getTerminalGroupId();

        if (!terminalGroupService.isTerminalGroupExists(terminalGroupId)) {
            throw new DigitalWaiterException.TerminalGroupWithThisIdNotFoundException(terminalGroupId);
        }

        if (restaurantTableRepository.existsByPosTableIdAndTerminalGroupId(
                request.getPosTableId(),
                terminalGroupId
        )) {
            throw new DigitalWaiterException.RestaurantTableAlreadyExistException();
        }

        RestaurantTable restaurantTable = restaurantTableMapper.createRestaurantTableRequestToRestaurantTable(request);

        return userService.createGuestUser(
                restaurantTableRepository.save(restaurantTable),
                organizationService.getOrganizationByTerminalGroupId(terminalGroupId).getName()
        );
    }

    public List<RestaurantTable> getRestaurantTables(Integer terminalGroupId) {
        if (terminalGroupId != null) {
            return getRestaurantTablesByTerminalGroupId(terminalGroupId);
        }

        return restaurantTableRepository.findAll();
    }

    public List<RestaurantTable> getRestaurantTablesByTerminalGroupId(Integer terminalGroupId) {
        if (!terminalGroupService.isTerminalGroupExists(terminalGroupId)) {
            throw new DigitalWaiterException.TerminalGroupWithThisIdNotFoundException(terminalGroupId);
        }

        return restaurantTableRepository.findByTerminalGroupId(terminalGroupId);
    }

    public RestaurantTableResponse getDetailedInformationAboutRestaurantTable(Integer id) {
        RestaurantTable restaurantTable = getRestaurantTable(id);
        User user = userService.getUserByRestaurantTableId(id);

        return restaurantTableMapper.restaurantTableToResponse(restaurantTable, user.getLogin());
    }

    @Transactional(transactionManager = "transactionManager")
    public void deleteRestaurantTable(Integer id) {
        RestaurantTable restaurantTable = getRestaurantTable(id);

        restaurantTableRepository.delete(restaurantTable);
    }

    @Transactional(transactionManager = "transactionManager")
    public void deleteRestaurantTablesByTerminalGroupId(Integer terminalGroupId) {
        if (!terminalGroupService.isTerminalGroupExists(terminalGroupId)) {
            throw new DigitalWaiterException.TerminalGroupWithThisIdNotFoundException(terminalGroupId);
        }

        restaurantTableRepository.deleteByTerminalGroupId(terminalGroupId);
    }

    @Transactional(transactionManager = "transactionManager")
    public RestaurantTableResponse updateRestaurantTable(Integer id, UpdateRestaurantTableRequest request) {
        RestaurantTable restaurantTable = getRestaurantTable(id);

        if (request.getTerminalGroupId() != null &&
                !terminalGroupService.isTerminalGroupExists(request.getTerminalGroupId())) {
            throw new DigitalWaiterException.TerminalGroupWithThisIdNotFoundException(request.getTerminalGroupId());
        }

        Integer targetTerminalGroupId = request.getTerminalGroupId() != null
                ? request.getTerminalGroupId()
                : restaurantTable.getTerminalGroupId();

        String targetPosTableId = request.getPosTableId() != null
                ? request.getPosTableId()
                : restaurantTable.getPosTableId();

        boolean terminalGroupChanged = !Objects.equals(targetTerminalGroupId, restaurantTable.getTerminalGroupId());
        boolean posTableIdChanged = !Objects.equals(targetPosTableId, restaurantTable.getPosTableId());

        if ((terminalGroupChanged || posTableIdChanged) &&
                restaurantTableRepository.existsByPosTableIdAndTerminalGroupId(
                        targetPosTableId,
                        targetTerminalGroupId
                )) {
            throw new DigitalWaiterException.RestaurantTableAlreadyExistException();
        }

        if (request.getName() != null) {
            restaurantTable.setName(request.getName());
        }
        if (request.getPosTableId() != null) {
            restaurantTable.setPosTableId(request.getPosTableId());
        }

        restaurantTable.setTerminalGroupId(targetTerminalGroupId);
        restaurantTable.setPosTableId(targetPosTableId);

        restaurantTableRepository.save(restaurantTable);
        User user = userService.updateUserByRestaurantTableId(id, request.getLogin(), request.getPassword());

        return restaurantTableMapper.restaurantTableToResponse(restaurantTable, user.getLogin());
    }
}
