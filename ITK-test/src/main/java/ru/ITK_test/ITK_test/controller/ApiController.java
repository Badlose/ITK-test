package ru.ITK_test.ITK_test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ITK_test.ITK_test.dto.income.TransferDto;
import ru.ITK_test.ITK_test.dto.outgo.BalanceDto;
import ru.ITK_test.ITK_test.dto.outgo.TransferStatusDto;
import ru.ITK_test.ITK_test.service.ApiService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Api", description = "API для управления переводами")
public class ApiController {

    private final ApiService apiService;

    @PostMapping("/wallet")
    @Operation(summary = "Перевод средств",
            tags = {"Api"},
            operationId = "transfer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = ""))
            }
    )
    private TransferStatusDto createTransfer(@Valid @RequestBody TransferDto dto) {
        return apiService.createTransfer(dto);
    }

    @GetMapping("/wallets/{walletUuid}")
    @Operation(summary = "Узнать баланс кошелька",
            tags = {"Api"},
            operationId = "getWalletBalance",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = ""))
            }
    )
    private BalanceDto getWalletBalance(@Parameter(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
                                        @PathVariable UUID walletUuid) {
        return apiService.getBalance(walletUuid);
    }

}
