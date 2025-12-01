package nsu.sber.domain.model.operation;

import lombok.Getter;

@Getter
public enum OperationState {
    SUCCESS ("Success"),
    ERROR ("Error"),
    IN_PROGRESS ("InProgress");

    private final String externalValue;

    OperationState(String externalValue) {
        this.externalValue = externalValue;
    }

    public static OperationState fromExternal(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (OperationState state : values()) {
            if (state.externalValue.equals(value)) {
                return state;
            }
        }
        return null;
    }
}
