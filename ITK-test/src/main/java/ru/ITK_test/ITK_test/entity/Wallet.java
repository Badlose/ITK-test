package ru.ITK_test.ITK_test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallet")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "wallet_uuid", nullable = false, unique = true)
    private UUID walletUuid;

    @NotNull
    @Column(name = "balance", nullable = false, scale = 2, precision = 19)
    private BigDecimal balance;

}
