package ru.ITK_test.ITK_test.mapper;

import ru.ITK_test.ITK_test.dto.outgo.BalanceDto;

import java.math.BigDecimal;

public interface BalanceMapper {

    static BalanceDto toBalanceDto(BigDecimal balance) {
        return BalanceDto.builder()
                .balance(balance)
                .build();
    }

}
