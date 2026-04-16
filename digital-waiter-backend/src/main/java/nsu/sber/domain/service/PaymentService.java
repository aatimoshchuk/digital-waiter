package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.payment.ChoosePaymentTypeRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final NotificationService notificationService;

    public void choosePaymentType(ChoosePaymentTypeRequest request) {
        notificationService.sendReadyToPayNotification(request);
    }
}
