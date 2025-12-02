package nsu.sber.voiceassistant.model;

public enum IntentType {
    ADD_ITEM("add_item"),
    REMOVE_ITEM("remove_item"),
    CHANGE_QUANTITY("change_quantity"),
    SHOW_CART("show_cart"),
    CALL_WAITER("call_waiter"),
    PAY("pay"),
    CONFIRM("confirm"),
    CANCEL("cancel"),
    HELP("help"),
    UNKNOWN("unknown");

    private final String value;

    IntentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
