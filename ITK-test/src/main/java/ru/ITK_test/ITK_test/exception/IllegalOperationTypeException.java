package ru.ITK_test.ITK_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ITK_test.ITK_test.exception.enums.ErrorType;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalOperationTypeException extends ApiParentException {

    public IllegalOperationTypeException(String message) {
        super(ErrorType.ILLEGAL_OPERATION_TYPE, "Incorrect operation type for transfer id: [%s]".formatted(message));
    }
}
