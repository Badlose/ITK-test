package ru.ITK_test.ITK_test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ITK_test.ITK_test.dto.income.TransferDto;
import ru.ITK_test.ITK_test.entity.OperationType;
import ru.ITK_test.ITK_test.entity.TransferStatus;
import ru.ITK_test.ITK_test.entity.Wallet;
import ru.ITK_test.ITK_test.repository.WalletRepository;
import ru.ITK_test.ITK_test.service.ApiService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ITK_test.ITK_test.helper.TestConstants.WALLET_UUID;
import static ru.ITK_test.ITK_test.helper.TestHelper.createFilledWalletBuilder;
import static ru.ITK_test.ITK_test.helper.TestHelper.getTransferDtoDepositBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ApiService service;
    @Autowired
    private WalletRepository walletRepository;

    @Test
    void shouldCreateTransferDeposit() throws Exception {
        UUID walletUuid = WALLET_UUID;
        TransferDto transferDto = getTransferDtoDepositBuilder()
                .walletId(walletUuid)
                .build();

        ResultActions perform = mockMvc.perform(post("/api/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferDto)));

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status").value(TransferStatus.SUCCESS.toString()));
    }

    @Test
    void shouldCreateTransferWithdraw() throws Exception {
        UUID walletUuid = WALLET_UUID;
        TransferDto transferDto = getTransferDtoDepositBuilder()
                .walletId(walletUuid)
                .operationType(OperationType.WITHDRAW)
                .build();

        ResultActions perform = mockMvc.perform(post("/api/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferDto)));

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status").value(TransferStatus.SUCCESS.toString()));
    }

    @Test
    void shouldGetBalance() throws Exception {
        UUID walletUuid = WALLET_UUID;
        Wallet wallet = createFilledWalletBuilder()
                .balance(BigDecimal.valueOf(100))
                .build();
        walletRepository.save(wallet);

        ResultActions perform = mockMvc.perform(get("/api/v1/wallets/{walletUuid}", walletUuid)
                .contentType(MediaType.APPLICATION_JSON));

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(100.0)));
    }

}
