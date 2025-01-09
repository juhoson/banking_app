package com.banking.domain.dto;

public record ExpenseCategoryDTO(
        String name,
        double amount,
        double percentage,
        String color
) {}