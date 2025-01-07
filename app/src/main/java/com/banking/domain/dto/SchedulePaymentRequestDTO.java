package com.banking.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SchedulePaymentRequestDTO(
    BigDecimal amount,
    LocalDateTime scheduledDate,
    String description
) {}
