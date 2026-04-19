package nsu.sber.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChoosePaymentTypeResponseDto {
    private String qrCodeUrl;
    private Double sum;
}
