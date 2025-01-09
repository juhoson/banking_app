package com.banking.domain.dto;

public record DashboardStatsDTO(
        double balance,
        double monthlyIncome,
        double monthlyExpenses,
        double savingsGoal,
        double currentSavings,
        BalanceChangeDTO balanceChange
) {}



