package nsu.sber.domain.port.pos;

import nsu.sber.domain.model.payment.AddOrderPaymentsRequest;
import nsu.sber.domain.model.payment.PaymentTypesRequest;
import nsu.sber.domain.model.payment.PaymentTypesResponse;

public interface PosPaymentPort {

    PaymentTypesResponse getPaymentTypes(PaymentTypesRequest paymentTypesRequest);

    void addOrderPayments(AddOrderPaymentsRequest addOrderPaymentsRequest);

}
