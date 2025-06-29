package ru.ITK_test.ITK_test.mapper;

import ru.ITK_test.ITK_test.dto.income.TransferDto;
import ru.ITK_test.ITK_test.dto.outgo.TransferStatusDto;
import ru.ITK_test.ITK_test.entity.Transfer;
import ru.ITK_test.ITK_test.entity.TransferStatus;

public interface TransferMapper {

    static Transfer.TransferBuilder createTransferBuilder(TransferDto dto) {
        return Transfer.builder()
                .walletId(dto.getWalletId())
                .operationType(dto.getOperationType())
                .amount(dto.getAmount());
    }

    static Transfer toEntity(TransferDto dto, TransferStatus status) {
        return createTransferBuilder(dto)
                .status(status)
                .build();
    }

    static TransferStatusDto toTransferStatusDto(Transfer transfer) {
        return TransferStatusDto.builder()
                .status(transfer.getStatus())
                .build();
    }

}
