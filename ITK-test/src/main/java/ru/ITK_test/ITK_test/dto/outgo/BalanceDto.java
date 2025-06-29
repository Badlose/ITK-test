package ru.ITK_test.ITK_test.dto.outgo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "DTO for balance display")
public class BalanceDto {

    private BigDecimal balance;

}
