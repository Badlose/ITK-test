package ru.ITK_test.ITK_test.exception;

import lombok.Getter;
import ru.ITK_test.ITK_test.exception.enums.ErrorType;

@Getter
public class ApiParentException extends RuntimeException {

    private final ErrorType errorType;

    public ApiParentException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

}
