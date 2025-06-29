package ru.ITK_test.ITK_test.helper;

import lombok.RequiredArgsConstructor;
import ru.ITK_test.ITK_test.dto.income.TransferDto;
import ru.ITK_test.ITK_test.dto.outgo.BalanceDto;
import ru.ITK_test.ITK_test.dto.outgo.TransferStatusDto;
import ru.ITK_test.ITK_test.entity.OperationType;
import ru.ITK_test.ITK_test.entity.Transfer;
import ru.ITK_test.ITK_test.entity.TransferStatus;
import ru.ITK_test.ITK_test.entity.Wallet;
import ru.ITK_test.ITK_test.repository.TransferRepository;
import ru.ITK_test.ITK_test.repository.WalletRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
public class TestHelper {

    private static final PodamFactory factory = new PodamFactoryImpl();
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;

    public static Transfer.TransferBuilder createTransferBuilder() {
        return Transfer.builder();
    }

    public static Transfer.TransferBuilder createFilledTransferBuilder() {
        return createTransferBuilder()
                .walletId(factory.manufacturePojo(UUID.class))
                .operationType(OperationType.DEPOSIT)
                .amount(BigDecimal.valueOf(1))
                .status(TransferStatus.SUCCESS);
    }

    public static Transfer createTransfer() {
        return createFilledTransferBuilder().build();
    }

    public static Transfer buildTransfer(Transfer.TransferBuilder builder) {
        return builder.build();
    }

    public static Wallet.WalletBuilder createWalletBuilder() {
        return Wallet.builder();
    }

    public static Wallet.WalletBuilder createFilledWalletBuilder() {
        return createWalletBuilder()
                .id(1L)
                .walletUuid(TestConstants.WALLET_UUID)
                .balance(BigDecimal.ZERO);
    }

    public static Wallet createWallet() {
        return createFilledWalletBuilder().build();
    }

    public static Wallet buildWallet(Wallet.WalletBuilder builder) {
        return builder.build();
    }

    public static TransferDto getTransferDto() {
        return factory.manufacturePojo(TransferDto.class);
    }

    public static TransferDto.TransferDtoBuilder getTransferDtoDepositBuilder() {
        return TransferDto.builder()
                .operationType(OperationType.DEPOSIT)
                .amount(BigDecimal.valueOf(1));
    }

    public static TransferDto.TransferDtoBuilder getTransferDtoWithdrawBuilder() {
        return TransferDto.builder()
                .operationType(OperationType.WITHDRAW)
                .amount(BigDecimal.valueOf(1));
    }

    public static BalanceDto getBalanceDto() {
        return factory.manufacturePojo(BalanceDto.class);
    }

    public static TransferStatusDto getTransferStatusDto() {
        return factory.manufacturePojo(TransferStatusDto.class);
    }

}
