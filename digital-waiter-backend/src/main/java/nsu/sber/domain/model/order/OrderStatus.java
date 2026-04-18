package nsu.sber.domain.model.order;

public enum OrderStatus {
    NEW("New"),
    BILL("Bill"),
    CLOSED("Closed");

    private final String externalValue;

    OrderStatus(String externalValue) {
        this.externalValue = externalValue;
    }

    public static OrderStatus fromExternal(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (OrderStatus status : values()) {
            if (status.externalValue.equals(value)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return externalValue;
    }
}
