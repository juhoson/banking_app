package com.banking.controller;

import com.banking.domain.ScheduledPayment;
import com.banking.domain.dto.SchedulePaymentRequestDTO;
import com.banking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{accountId}/schedule")
    public ResponseEntity<ScheduledPayment> schedulePayment(
            @PathVariable String accountId,
            @RequestBody SchedulePaymentRequestDTO request
    ) {
        return ResponseEntity.ok(paymentService.schedulePayment(request, accountId));
    }

    @GetMapping("/{accountId}/scheduled")
    public ResponseEntity<List<ScheduledPayment>> getScheduledPayments(
            @PathVariable String accountId
    ) {
        return ResponseEntity.ok(paymentService.getScheduledPayments(accountId));
    }
}