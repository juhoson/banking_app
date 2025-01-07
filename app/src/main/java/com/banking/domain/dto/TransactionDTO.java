package com.banking.domain.dto;

import com.banking.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
    String id,
    BigDecimal amount,
    TransactionType type,
    LocalDateTime timestamp,
    String description
) {}
