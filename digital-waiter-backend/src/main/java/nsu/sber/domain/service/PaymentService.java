package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.payment.ChoosePaymentTypeRequest;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final NotificationService notificationService;

    private final OrderService orderService;
    private final RestaurantTableService restaurantTableService;
    private final TerminalGroupService terminalGroupService;
    private final OrganizationService organizationService;

    public void choosePaymentType(ChoosePaymentTypeRequest request) {
        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();
        TerminalGroup terminalGroup = terminalGroupService.getTerminalGroup(restaurantTable.getTerminalGroupId());
        Organization organization = organizationService.getOrganization(terminalGroup.getOrganizationId());

        if (!orderService.hasOpenOrders(organization.getPosOrganizationId(), restaurantTable.getPosTableId())) {
            throw new DigitalWaiterException.OpenOrderNotFoundException();
        }

        notificationService.sendReadyToPayNotification(request, restaurantTable, terminalGroup);
    }
}
