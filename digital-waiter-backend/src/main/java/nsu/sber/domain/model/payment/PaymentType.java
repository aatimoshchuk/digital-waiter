package nsu.sber.domain.model.payment;

public enum PaymentType {
    CASH("наличные"),
    CARD("карта");

    private final String value;

    PaymentType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
