package ru.ITK_test.ITK_test.dto.outgo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.ITK_test.ITK_test.entity.TransferStatus;

@Data
@Builder
@Schema(description = "DTO for transfer status display")
public class TransferStatusDto {

    private TransferStatus status;
}
