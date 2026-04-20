package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.order.Order;
import nsu.sber.domain.model.payment.AddOrderPaymentsRequest;
import nsu.sber.domain.model.payment.ChoosePaymentTypeRequest;
import nsu.sber.domain.model.payment.ChoosePaymentTypeResponse;
import nsu.sber.domain.model.payment.ConfirmQRCodePaymentRequest;
import nsu.sber.domain.model.payment.GetPaymentTypesResponse;
import nsu.sber.domain.model.payment.PaymentType;
import nsu.sber.domain.model.payment.PaymentTypesRequest;
import nsu.sber.domain.model.payment.PaymentTypesResponse;
import nsu.sber.domain.port.pos.PosPaymentPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final NotificationService notificationService;

    private final OrderService orderService;
    private final RestaurantTableService restaurantTableService;
    private final TerminalGroupService terminalGroupService;
    private final OrganizationService organizationService;

    private final PosPaymentPort posPaymentPort;

    @Value("${payment.qr.url}")
    private String qrCodeUrl;

    public ChoosePaymentTypeResponse choosePaymentType(ChoosePaymentTypeRequest request) {
        if (Objects.equals(request.getPaymentTypeCode(), "QR")) {
            return initiateQRCodePayment(request);
        }

        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();
        TerminalGroup terminalGroup = terminalGroupService.getTerminalGroup(restaurantTable.getTerminalGroupId());
        Organization organization = organizationService.getOrganization(terminalGroup.getOrganizationId());

        if (!orderService.isOrderExists(organization.getPosOrganizationId(), request.getOrderId())) {
            throw new DigitalWaiterException.OrderNotFoundException(request.getOrderId());
        }

        notificationService.sendReadyToPayNotification(request, restaurantTable, terminalGroup);

        return ChoosePaymentTypeResponse
                .builder()
                .build();
    }

    public GetPaymentTypesResponse getPaymentTypes() {
        TerminalGroup terminalGroup = terminalGroupService.getCurrentTerminalGroup();
        Organization organization = organizationService.getOrganization(terminalGroup.getOrganizationId());

        return GetPaymentTypesResponse
                .builder()
                .paymentTypes(getPaymentTypes(terminalGroup.getPosTerminalGroupId(), organization.getPosOrganizationId()))
                .build();
    }

    /**
     * ⚠️ STUB implementation of QR payment.
     * Used for development/testing because no payment provider (Yandex Pay, SBP, etc.) is connected yet.
     */
    public void confirmQRCodePayment(ConfirmQRCodePaymentRequest request) {
        Organization organization = organizationService.getCurrentOrganization();

        Order order = orderService.getOrderById(organization.getPosOrganizationId(), request.getOrderId());

        if (order.getProcessedPaymentsSum() != 0) {
            throw new DigitalWaiterException.PaymentAlreadyProcessedException();
        }

        PaymentType paymentType = getPaymentTypeByCode("QR")
                .orElseThrow(() -> new DigitalWaiterException.PaymentTypeNotFoundException("QR"));

        posPaymentPort.addOrderPayments(buildAddOrderPaymentRequest(
                request.getOrderId(),
                organization.getPosOrganizationId(),
                request.getSum(),
                paymentType
        ));
    }

    /**
     * ⚠️ STUB implementation of QR payment.
     * Used for development/testing because no payment provider (Yandex Pay, SBP, etc.) is connected yet.
     */
    private ChoosePaymentTypeResponse initiateQRCodePayment(ChoosePaymentTypeRequest request) {
        Order order = orderService.getOrderById(
                organizationService.getCurrentOrganization().getPosOrganizationId(),
                request.getOrderId()
        );

        if (order.getProcessedPaymentsSum() != 0) {
            throw new DigitalWaiterException.PaymentAlreadyProcessedException();
        }

        return ChoosePaymentTypeResponse
                .builder()
                .qrCodeUrl(qrCodeUrl)
                .sum(order.getSum())
                .build();
    }

    private List<PaymentType> getPaymentTypes(String posTerminalGroupId, String posOrganizationId) {
        PaymentTypesResponse response = posPaymentPort.getPaymentTypes(
                new PaymentTypesRequest(List.of(posOrganizationId))
        );

        return filterPaymentTypes(response.getPaymentTypes(), posTerminalGroupId);
    }

    private Optional<PaymentType> getPaymentTypeByCode(String code) {
        TerminalGroup terminalGroup = terminalGroupService.getCurrentTerminalGroup();
        Organization organization = organizationService.getOrganization(terminalGroup.getOrganizationId());

        List<PaymentType> paymentTypes = getPaymentTypes(terminalGroup.getPosTerminalGroupId(), organization.getPosOrganizationId());

        return paymentTypes
                .stream()
                .filter(paymentType -> paymentType.getCode().equals(code))
                .findAny();
    }

    private List<PaymentType> filterPaymentTypes(List<PaymentType> paymentTypes, String terminalGroupId) {
        return paymentTypes
                .stream()
                .filter(paymentType ->
                        paymentType.getTerminalGroups() != null &&
                                paymentType.getTerminalGroups().stream()
                                        .anyMatch(tg -> terminalGroupId.equals(tg.getId()))
                )
                .toList();
    }

    private AddOrderPaymentsRequest buildAddOrderPaymentRequest(
            String orderId,
            String posOrganizationId,
            Double sum,
            PaymentType paymentType
    ) {
        AddOrderPaymentsRequest.Payment payment = AddOrderPaymentsRequest.Payment
                .builder()
                .paymentTypeKind(paymentType.getPaymentTypeKind())
                .sum(sum)
                .paymentTypeId(paymentType.getId())
                .build();

        return AddOrderPaymentsRequest
                .builder()
                .orderId(orderId)
                .organizationId(posOrganizationId)
                .payments(List.of(payment))
                .build();
    }
}
