package com.banking.controller;

import com.banking.domain.dto.AccountBalanceDTO;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<AccountBalanceDTO> getBalance(@PathVariable String accountId) {
        return ResponseEntity.ok(accountService.getBalance(accountId));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Void> deposit(
            @PathVariable String accountId,
            @RequestParam BigDecimal amount
    ) {
        accountService.deposit(accountId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Void> withdraw(
            @PathVariable String accountId,
            @RequestParam BigDecimal amount
    ) {
        accountService.withdraw(accountId, amount);
        return ResponseEntity.ok().build();
    }
}