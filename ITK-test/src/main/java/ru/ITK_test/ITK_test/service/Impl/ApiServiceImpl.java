package ru.ITK_test.ITK_test.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ITK_test.ITK_test.dto.income.TransferDto;
import ru.ITK_test.ITK_test.dto.outgo.BalanceDto;
import ru.ITK_test.ITK_test.dto.outgo.TransferStatusDto;
import ru.ITK_test.ITK_test.entity.OperationType;
import ru.ITK_test.ITK_test.entity.Transfer;
import ru.ITK_test.ITK_test.entity.TransferStatus;
import ru.ITK_test.ITK_test.entity.Wallet;
import ru.ITK_test.ITK_test.exception.IllegalOperationTypeException;
import ru.ITK_test.ITK_test.exception.InsufficientFundsException;
import ru.ITK_test.ITK_test.exception.WalletNotFoundException;
import ru.ITK_test.ITK_test.mapper.BalanceMapper;
import ru.ITK_test.ITK_test.mapper.TransferMapper;
import ru.ITK_test.ITK_test.repository.TransferRepository;
import ru.ITK_test.ITK_test.repository.WalletRepository;
import ru.ITK_test.ITK_test.service.ApiService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransferRepository transferRepository;

    @Override
    @Transactional
    public TransferStatusDto createTransfer(TransferDto dto) {

        Transfer transfer = TransferMapper.toEntity(dto, TransferStatus.PENDING);
        Wallet wallet = findWalletById(dto.getWalletId());
        BigDecimal amount = dto.getAmount();
        BigDecimal walletBalance = wallet.getBalance();
        OperationType operationType = dto.getOperationType();

        try {
            switch (operationType) {
                case DEPOSIT -> wallet.setBalance(walletBalance.add(amount));
                case WITHDRAW -> {
                    BigDecimal newBalance = wallet.getBalance().subtract(amount);
                    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                        throw new InsufficientFundsException(wallet.getId().toString());
                    }
                    wallet.setBalance(newBalance);
                }
                default -> throw new IllegalOperationTypeException(transfer.getId().toString());
            }
            roundBalance(wallet);
            walletRepository.save(wallet);
            transfer.setStatus(TransferStatus.SUCCESS);
        } catch (InsufficientFundsException | IllegalOperationTypeException e) {
            transfer.setStatus(TransferStatus.FAILED);
            throw e;
        } finally {
            transferRepository.save(transfer);
        }
        return TransferMapper.toTransferStatusDto(transfer);
    }

    @Override
    @Transactional
    public BalanceDto getBalance(UUID walletUuid) {
        Wallet wallet = findWalletById(walletUuid);
        return BalanceMapper.toBalanceDto(wallet.getBalance());
    }

    private Wallet findWalletById(UUID uuid) {
        return walletRepository.findByWalletForUpdate(uuid).orElseThrow(
                () -> new WalletNotFoundException(uuid.toString()));
    }

    private void roundBalance(Wallet wallet) {
        wallet.setBalance(wallet.getBalance().setScale(2, RoundingMode.HALF_UP));
    }

}
