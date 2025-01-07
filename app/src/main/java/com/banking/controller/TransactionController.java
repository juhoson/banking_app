package com.banking.controller;

import com.banking.domain.Transaction;
import com.banking.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class TransactionController {
    private static final Logger LOGGER = LogManager.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String accountId) {
        LOGGER.info("Getting transaction history for account: {}", accountId);
        List<Transaction> transactions = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{accountId}/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(
            @PathVariable String accountId,
            @PathVariable String transactionId
    ) {
        LOGGER.info("Getting transaction details - accountId: {}, transactionId: {}", accountId, transactionId);
        return transactionService.getTransaction(accountId, transactionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}