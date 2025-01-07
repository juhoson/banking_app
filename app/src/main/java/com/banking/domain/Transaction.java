package com.banking.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private String id;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String description;

    // Default constructor required by JPA
    protected Transaction() {}

    public Transaction(String id, String accountId, BigDecimal amount,
                       TransactionType type, LocalDateTime timestamp, String description) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                '}';
    }
}