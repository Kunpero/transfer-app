package rs.mladost.transfer.exception;

import lombok.Getter;

@Getter
public enum ErrorType {
    INTERNAL_ERROR(1),
    VALIDATION_ERROR(2),
    OPERATION_ERROR(3);

    private int value;

    ErrorType(int value) {
        this.value = value;
    }
}
