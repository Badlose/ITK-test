package ru.ITK_test.ITK_test.service;

import ru.ITK_test.ITK_test.dto.income.TransferDto;
import ru.ITK_test.ITK_test.dto.outgo.BalanceDto;
import ru.ITK_test.ITK_test.dto.outgo.TransferStatusDto;

import java.util.UUID;

public interface ApiService {
    TransferStatusDto createTransfer(TransferDto dto);

    BalanceDto getBalance(UUID walletUuid);

}
