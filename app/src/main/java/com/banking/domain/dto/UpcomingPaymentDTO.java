package com.banking.domain.dto;

import java.time.LocalDate;

public record UpcomingPaymentDTO(
        Long id,
        String name,
        double amount,
        LocalDate dueDate,
        long daysUntilDue
) {}