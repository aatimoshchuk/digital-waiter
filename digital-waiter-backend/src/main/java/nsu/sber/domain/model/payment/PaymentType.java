package nsu.sber.domain.model.payment;

public enum PaymentType {
    CASH("наличные"),
    CARD("карта"),
    QR("qr-код");

    private final String value;

    PaymentType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
