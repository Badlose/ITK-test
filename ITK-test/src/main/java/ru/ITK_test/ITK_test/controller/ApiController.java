package ru.ITK_test.ITK_test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ITK_test.ITK_test.dto.income.TransferDto;
import ru.ITK_test.ITK_test.dto.outgo.BalanceDto;
import ru.ITK_test.ITK_test.dto.outgo.TransferStatusDto;
import ru.ITK_test.ITK_test.service.ApiService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "api/v1")
@Tag(name = "Api", description = "API для управления переводами")
public class ApiController {

    private final ApiService apiService;

    @PostMapping("/wallet")
    @Operation(summary = "Перевод средств",
            tags = {"Api"},
            operationId = "transfer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferStatusDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "")),
            }
    )
    private TransferStatusDto transfer(@RequestBody TransferDto dto) {
        return apiService.transfer(dto);
    }

    @GetMapping("/wallets/{walletUuid}")
    @Operation(summary = "Узнать баланс",
            tags = {"Api"},
            operationId = "getWalletBalance",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BalanceDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "")),
            }
    )
    private BalanceDto getWalletBalance(@RequestBody UUID walletUuid) {
        return apiService.getBalance(walletUuid);
    }
}
