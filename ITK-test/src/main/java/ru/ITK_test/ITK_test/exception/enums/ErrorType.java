package ru.ITK_test.ITK_test.exception.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ErrorType {
    @Schema(name = "Wallet not found by wallet UUID")
    WALLET_NOT_FOUND_BY_WALLET_UUID,
    @Schema(name = "There are not enough funds in this wallet.")
    INSUFFICIENT_FUNDS,
    @Schema(name = "The wrong type of operation was transmitted")
    ILLEGAL_OPERATION_TYPE,

}
