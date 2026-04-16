package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.service.PaymentService;
import nsu.sber.web.dto.ChoosePaymentTypeRequestDto;
import nsu.sber.web.mapper.PaymentDtoMapper;
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
    public void choosePaymentType(@RequestBody @Valid ChoosePaymentTypeRequestDto choosePaymentTypeRequestDto) {
        paymentService.choosePaymentType(paymentDtoMapper.dtoToChoosePaymentTypeRequest(choosePaymentTypeRequestDto));
    }
}
