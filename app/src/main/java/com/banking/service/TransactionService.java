package com.banking.service;

import com.banking.domain.Transaction;
import com.banking.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private static final Logger LOGGER = LogManager.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionHistory(String accountId) {
        LOGGER.info("Fetching transaction history for account: {}", accountId);
        return transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
    }

    @Transactional(readOnly = true)
    public Optional<Transaction> getTransaction(String accountId, String transactionId) {
        LOGGER.info("Fetching transaction - accountId: {}, transactionId: {}", accountId, transactionId);
        return transactionRepository.findById(transactionId)
                .filter(transaction -> transaction.getAccountId().equals(accountId));
    }

    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        LOGGER.info("Saving transaction for account: {}", transaction.getAccountId());
        return transactionRepository.save(transaction);
    }
}