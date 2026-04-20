package nsu.sber.messaging.pos.iiko.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentTypesResponseDto {
    private List<PaymentType> paymentTypes;

    @Data
    public static class PaymentType {
        private String id;
        private String code;
        private String name;
        private String paymentTypeKind;
        private List<TerminalGroup> terminalGroups;

        @Data
        public static class TerminalGroup {
            private String id;
        }
    }
}
