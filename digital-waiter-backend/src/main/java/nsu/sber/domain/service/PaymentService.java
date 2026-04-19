package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.order.GetOrdersResponse;
import nsu.sber.domain.model.payment.ChoosePaymentTypeRequest;
import nsu.sber.domain.model.payment.ChoosePaymentTypeResponse;
import nsu.sber.domain.model.payment.PaymentType;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final NotificationService notificationService;

    private final OrderService orderService;
    private final RestaurantTableService restaurantTableService;
    private final TerminalGroupService terminalGroupService;
    private final OrganizationService organizationService;

    @Value("${payment.qr.url}")
    private String qrCodeUrl;

    public ChoosePaymentTypeResponse choosePaymentType(ChoosePaymentTypeRequest request) {
        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();
        TerminalGroup terminalGroup = terminalGroupService.getTerminalGroup(restaurantTable.getTerminalGroupId());
        Organization organization = organizationService.getOrganization(terminalGroup.getOrganizationId());

        GetOrdersResponse response = orderService.getOrderById(
                organization.getPosOrganizationId(),
                request.getOrderId()
        );

        if (response.getOrders().isEmpty()) {
            throw new DigitalWaiterException.OrderNotFoundException(request.getOrderId());
        }

        if (request.getPaymentType() == PaymentType.QR) {
            return ChoosePaymentTypeResponse
                    .builder()
                    .qrCodeUrl(qrCodeUrl)
                    .sum(response.getOrders().get(0).getOrder().getSum())
                    .build();
        }

        notificationService.sendReadyToPayNotification(request, restaurantTable, terminalGroup);

        return ChoosePaymentTypeResponse
                .builder()
                .build();
    }
}
