package com.banking.domain.dto;

import com.banking.domain.AccountStatus;

import java.math.BigDecimal;

public record AccountBalanceDTO(
    String accountId,
    BigDecimal balance,
    AccountStatus status
) {}

