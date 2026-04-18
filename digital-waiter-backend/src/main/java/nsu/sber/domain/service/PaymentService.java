package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.payment.ChoosePaymentTypeRequest;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final NotificationService notificationService;
    private final OrderService orderService;

    public void choosePaymentType(ChoosePaymentTypeRequest request) {
        if (!orderService.hasOpenOrders()) {
            throw new DigitalWaiterException.OpenOrderNotFoundException();
        }

        notificationService.sendReadyToPayNotification(request);
    }
}
