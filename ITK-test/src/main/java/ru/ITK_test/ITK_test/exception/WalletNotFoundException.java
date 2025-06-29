package ru.ITK_test.ITK_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ITK_test.ITK_test.exception.enums.ErrorType;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WalletNotFoundException extends ApiParentException {

    public WalletNotFoundException(String message) {
        super(ErrorType.WALLET_NOT_FOUND_BY_WALLET_UUID, "Wallet not found by wallet UUID: [%s]".formatted(message));
    }
}
