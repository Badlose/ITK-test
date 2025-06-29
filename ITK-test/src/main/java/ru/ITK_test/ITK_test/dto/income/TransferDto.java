package ru.ITK_test.ITK_test.dto.income;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ITK_test.ITK_test.entity.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for transfers")
@Builder
public class TransferDto {

    @NotNull(message = "Wallet UUID is required")
    private UUID walletId;

    @NotNull(message = "OperationType is required")
    private OperationType operationType;

    @NotNull(message = "Amount of operation is required")
    private BigDecimal amount;
}
