package nsu.sber.domain.model.webhooks;

public enum WebHookEventType {
    STOP_LIST_UPDATE("StopListUpdate"),
    TABLE_ORDER_UPDATE("TableOrderUpdate"),
    TABLE_ORDER_ERROR("TableOrderError"),
    UNKNOWN("UNKNOWN");

    private final String value;

    WebHookEventType(String value) {
        this.value = value;
    }

    public static WebHookEventType fromValue(String value) {
        if (value == null) {
            return UNKNOWN;
        }

        for (WebHookEventType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        return UNKNOWN;
    }

}
