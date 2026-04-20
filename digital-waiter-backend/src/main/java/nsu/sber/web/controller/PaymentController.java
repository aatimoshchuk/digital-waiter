package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.PaymentService;
import nsu.sber.web.dto.ChoosePaymentTypeRequestDto;
import nsu.sber.web.dto.ChoosePaymentTypeResponseDto;
import nsu.sber.web.dto.ConfirmQRCodePaymentRequestDto;
import nsu.sber.web.dto.GetPaymentTypesResponseDto;
import nsu.sber.web.mapper.PaymentDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@Tag(name = "Payment Controller")
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentDtoMapper paymentDtoMapper;

    @PostMapping("/type")
    @Operation(
            summary = "Choose payment type for the order",
            description = """
                    Choose payment type for the order
                    and notify the waiter of the need to accept payment / initiate payment
                    """
    )
    public ChoosePaymentTypeResponseDto choosePaymentType(@RequestBody @Valid ChoosePaymentTypeRequestDto requestDto) {
        return paymentDtoMapper.choosePaymentTypeResponseToDto(paymentService.choosePaymentType(
                paymentDtoMapper.dtoToChoosePaymentTypeRequest(requestDto)
        ));
    }

    @GetMapping("/type")
    @Operation(
            summary = "Get available payment types",
            description = "Get payment types available for the current terminal group"
    )
    public GetPaymentTypesResponseDto getPaymentTypes() {
        return paymentDtoMapper.getPaymentTypesResponseToDto(paymentService.getPaymentTypes());
    }

    @PostMapping("/qr/confirm")
    public void confirmQRCodePayment(@RequestBody @Valid ConfirmQRCodePaymentRequestDto requestDto) {
        paymentService.confirmQRCodePayment(paymentDtoMapper.dtoToConfirmQRCodePaymentRequest(requestDto));
    }
}
