package com.banking.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Entity
@Table(name = "scheduled_payments")
public class ScheduledPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime scheduledDate;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR2(20)") // Add this
    private PaymentStatus status;

    // Default constructor required by JPA
    protected ScheduledPayment() {}

    public ScheduledPayment(String accountId, BigDecimal amount,
                            LocalDateTime scheduledDate, String description,
                            PaymentStatus status) {
        this.accountId = accountId;
        this.amount = amount;
        this.scheduledDate = scheduledDate;
        this.description = description;
        this.status = status;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public String getDescription() {
        return description;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public static ScheduledPayment createPayment(String accountId, BigDecimal amount,
                                                 LocalDateTime scheduledDate, String description) {
        return new ScheduledPayment(
                accountId,
                amount,
                scheduledDate.atZone(ZoneId.systemDefault())
                        .withZoneSameInstant(ZoneOffset.UTC)
                        .toLocalDateTime(),
                description,
                PaymentStatus.PENDING
        );
    }
}