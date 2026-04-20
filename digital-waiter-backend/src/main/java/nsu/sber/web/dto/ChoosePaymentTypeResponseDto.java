package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChoosePaymentTypeResponseDto {

    @Schema(description = "Link to QR code for payment (not null if payment by QR code is selected)")
    private String qrCodeUrl;

    @Schema(description = "The amount for which the order must be paid (not null if payment by QR code is selected)")
    private Double sum;
}
