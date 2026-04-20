package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import nsu.sber.util.ApiConstants;

import java.util.List;

@Data
public class GetPaymentTypesResponseDto {
    private List<PaymentType> paymentTypes;

    @Data
    public static class PaymentType {

        @Schema(
                example = ApiConstants.PAYMENT_TYPE_CODE_EXAMPLE,
                description = ApiConstants.PAYMENT_TYPE_CODE_DESCRIPTION
        )
        private String code;

        @Schema(
                example = ApiConstants.PAYMENT_TYPE_NAME_EXAMPLE,
                description = ApiConstants.PAYMENT_TYPE_NAME_DESCRIPTION
        )
        private String name;

    }
}
