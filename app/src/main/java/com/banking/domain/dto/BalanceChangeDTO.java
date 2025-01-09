package com.banking.domain.dto;

public record BalanceChangeDTO(
        double percentage,
        TrendType trend
) {}