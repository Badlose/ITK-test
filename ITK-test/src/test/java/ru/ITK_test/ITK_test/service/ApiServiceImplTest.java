package ru.ITK_test.ITK_test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ITK_test.ITK_test.dto.income.TransferDto;
import ru.ITK_test.ITK_test.dto.outgo.BalanceDto;
import ru.ITK_test.ITK_test.dto.outgo.TransferStatusDto;
import ru.ITK_test.ITK_test.entity.TransferStatus;
import ru.ITK_test.ITK_test.entity.Wallet;
import ru.ITK_test.ITK_test.exception.InsufficientFundsException;
import ru.ITK_test.ITK_test.exception.WalletNotFoundException;
import ru.ITK_test.ITK_test.helper.TestConstants;
import ru.ITK_test.ITK_test.repository.TransferRepository;
import ru.ITK_test.ITK_test.repository.WalletRepository;
import ru.ITK_test.ITK_test.service.Impl.ApiServiceImpl;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.ITK_test.ITK_test.helper.TestHelper.*;

@ExtendWith(MockitoExtension.class)
public class ApiServiceImplTest {

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private TransferRepository transferRepository;
    @InjectMocks
    private ApiServiceImpl service;

    @Test
    void shouldCreateTransferDeposit() {
        UUID walletUuid = TestConstants.WALLET_UUID;
        TransferDto transferDto = getTransferDtoDepositBuilder()
                .walletId(walletUuid)
                .amount(BigDecimal.valueOf(100))
                .build();
        Wallet wallet = createFilledWalletBuilder().build();

        when(walletRepository.findByWalletForUpdate(walletUuid)).thenReturn(Optional.of(wallet));

        TransferStatusDto response = service.createTransfer(transferDto);

        verify(walletRepository, times(1)).findByWalletForUpdate(walletUuid);
        assertThat(wallet.getBalance().compareTo(transferDto.getAmount())).isEqualTo(0);
        assertThat(response.getStatus()).isEqualTo(TransferStatus.SUCCESS);
    }

    @Test
    void shouldCreateTransferWithdraw() {
        UUID walletUuid = TestConstants.WALLET_UUID;
        TransferDto transferDto = getTransferDtoWithdrawBuilder()
                .walletId(walletUuid)
                .amount(BigDecimal.valueOf(100))
                .build();
        Wallet wallet = createFilledWalletBuilder()
                .balance(BigDecimal.valueOf(100))
                .build();

        when(walletRepository.findByWalletForUpdate(walletUuid)).thenReturn(Optional.of(wallet));

        TransferStatusDto response = service.createTransfer(transferDto);

        verify(walletRepository, times(1)).findByWalletForUpdate(walletUuid);
        assertThat(response).isNotNull();
        assertThat(wallet.getBalance().compareTo(BigDecimal.ZERO)).isEqualTo(0);
        assertThat(response.getStatus()).isEqualTo(TransferStatus.SUCCESS);
    }

    @Test
    void shouldThrowInsufficientFundsException() {
        UUID walletUuid = TestConstants.WALLET_UUID;
        TransferDto transferDto = getTransferDtoWithdrawBuilder()
                .walletId(walletUuid)
                .amount(BigDecimal.valueOf(100))
                .build();
        Wallet wallet = createFilledWalletBuilder()
                .balance(BigDecimal.valueOf(10))
                .build();

        when(walletRepository.findByWalletForUpdate(walletUuid)).thenReturn(Optional.of(wallet));

        assertThrows(InsufficientFundsException.class, () -> service.createTransfer(transferDto));
    }

    @Test
    void shouldThrowWalletNotFoundException() {
        UUID walletUuid = TestConstants.WALLET_WRONG_UUID;
        TransferDto transferDto = getTransferDtoDepositBuilder()
                .walletId(walletUuid)
                .amount(BigDecimal.valueOf(100))
                .build();

        assertThrows(WalletNotFoundException.class, () -> service.createTransfer(transferDto));
    }

    @Test
    void shouldGetBalance() {
        UUID walletUuid = TestConstants.WALLET_UUID;
        Wallet wallet = createFilledWalletBuilder()
                .balance(BigDecimal.valueOf(100))
                .build();

        when(walletRepository.findByWalletForUpdate(walletUuid)).thenReturn(Optional.ofNullable(wallet));

        BalanceDto response = service.getBalance(walletUuid);

        verify(walletRepository, times(1)).findByWalletForUpdate(walletUuid);
        assertThat(response).isNotNull();
        assertThat(response.getBalance().compareTo(Objects.requireNonNull(wallet).getBalance())).isEqualTo(0);
    }

    @Test
    void shouldRoundBalanceDown() {
        UUID walletUuid = TestConstants.WALLET_UUID;
        TransferDto transferDto = getTransferDtoDepositBuilder()
                .walletId(walletUuid)
                .amount(BigDecimal.valueOf(100.1234))
                .build();
        Wallet wallet = createFilledWalletBuilder()
                .balance(BigDecimal.valueOf(0))
                .build();

        when(walletRepository.findByWalletForUpdate(walletUuid)).thenReturn(Optional.of(wallet));

        TransferStatusDto response = service.createTransfer(transferDto);

        verify(walletRepository, times(1)).findByWalletForUpdate(walletUuid);
        assertThat(wallet.getBalance()).isEqualTo(BigDecimal.valueOf(100.12));
        assertThat(response.getStatus()).isEqualTo(TransferStatus.SUCCESS);
    }

    @Test
    void shouldRoundBalanceUp() {
        UUID walletUuid = TestConstants.WALLET_UUID;
        TransferDto transferDto = getTransferDtoDepositBuilder()
                .walletId(walletUuid)
                .amount(BigDecimal.valueOf(100.125))
                .build();
        Wallet wallet = createFilledWalletBuilder()
                .balance(BigDecimal.valueOf(0))
                .build();

        when(walletRepository.findByWalletForUpdate(walletUuid)).thenReturn(Optional.of(wallet));

        TransferStatusDto response = service.createTransfer(transferDto);

        verify(walletRepository, times(1)).findByWalletForUpdate(walletUuid);
        assertThat(wallet.getBalance()).isEqualTo(BigDecimal.valueOf(100.13));
        assertThat(response.getStatus()).isEqualTo(TransferStatus.SUCCESS);
    }

}
