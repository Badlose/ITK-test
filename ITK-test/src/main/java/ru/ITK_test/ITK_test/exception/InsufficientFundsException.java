package ru.ITK_test.ITK_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ITK_test.ITK_test.exception.enums.ErrorType;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientFundsException extends ApiParentException {

    public InsufficientFundsException(String message) {
        super(ErrorType.INSUFFICIENT_FUNDS, "There are not enough funds in this wallet: [%s]".formatted(message));
    }
}
