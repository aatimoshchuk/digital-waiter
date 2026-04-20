package nsu.sber.messaging.pos.iiko.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.payment.AddOrderPaymentsRequest;
import nsu.sber.domain.model.payment.PaymentTypesRequest;
import nsu.sber.domain.model.payment.PaymentTypesResponse;
import nsu.sber.domain.port.pos.PosPaymentPort;
import nsu.sber.messaging.pos.iiko.client.IikoClient;
import nsu.sber.messaging.pos.iiko.dto.PaymentTypesResponseDto;
import nsu.sber.messaging.pos.iiko.mapper.PaymentMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PosPaymentAdapter implements PosPaymentPort {
    private final PaymentMapper paymentMapper;
    private final IikoClient iikoClient;

    @Override
    public PaymentTypesResponse getPaymentTypes(PaymentTypesRequest paymentTypesRequest) {
        PaymentTypesResponseDto responseDto = iikoClient.getPaymentTypes(
                null,
                paymentMapper.paymentTypesRequestToDto(paymentTypesRequest)
        );

        return paymentMapper.dtoToPaymentTypesResponse(responseDto);
    }

    @Override
    public void addOrderPayments(AddOrderPaymentsRequest addOrderPaymentsRequest) {
        iikoClient.addOrderPayments(
                null,
                paymentMapper.addOrderPaymentsRequestToDto(addOrderPaymentsRequest)
        );
    }
}
