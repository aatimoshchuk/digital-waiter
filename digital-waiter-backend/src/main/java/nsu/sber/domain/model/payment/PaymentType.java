package nsu.sber.domain.model.payment;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentType {
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
