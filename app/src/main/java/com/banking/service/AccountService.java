package com.banking.service;

import com.banking.domain.Account;
import com.banking.domain.Transaction;
import com.banking.domain.TransactionType;
import com.banking.domain.dto.AccountBalanceDTO;
import com.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final KafkaProducerService kafkaProducer;
    private final TransactionService transactionService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          KafkaProducerService kafkaProducer, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.kafkaProducer = kafkaProducer;
        this.transactionService = transactionService;
    }

    @Transactional
    public AccountBalanceDTO getBalance(String accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
            
        return new AccountBalanceDTO(
            account.getId(),
            account.getBalance(),
            account.getStatus()
        );
    }
    @Transactional
    public void deposit(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.deposit(amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                accountId,
                amount,
                TransactionType.DEPOSIT,
                LocalDateTime.now(),
                "Deposit"
        );

        transactionService.saveTransaction(transaction);
        kafkaProducer.sendTransaction(transaction);
    }

    @Transactional
    public void withdraw(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.withdraw(amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                accountId,
                amount.negate(),
                TransactionType.WITHDRAWAL,
                LocalDateTime.now(),
                "Withdrawal"
        );

        transactionService.saveTransaction(transaction);
        kafkaProducer.sendTransaction(transaction);
    }
}