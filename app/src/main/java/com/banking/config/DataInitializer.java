package com.banking.config;

import com.banking.domain.Account;
import com.banking.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger LOGGER = LogManager.getLogger(DataInitializer.class);
    private final AccountRepository accountRepository;

    @Autowired
    public DataInitializer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {
        if (accountRepository.count() == 0) {
            LOGGER.info("Initializing test account...");
            Account testAccount = new Account("TEST123", new BigDecimal("1000.00"));
            accountRepository.save(testAccount);
            LOGGER.info("Test account created with ID: {}", testAccount.getId());
        }
    }
}