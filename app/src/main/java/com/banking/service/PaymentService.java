package com.banking.service;

import com.banking.domain.ScheduledPayment;
import com.banking.domain.PaymentStatus;
import com.banking.domain.dto.SchedulePaymentRequestDTO;
import com.banking.repository.ScheduledPaymentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    private static final Logger LOGGER = LogManager.getLogger(PaymentService.class);

    private final ScheduledPaymentRepository paymentRepository;
    private final AccountService accountService;

    @Autowired
    public PaymentService(ScheduledPaymentRepository paymentRepository,
                          AccountService accountService) {
        this.paymentRepository = paymentRepository;
        this.accountService = accountService;
    }

    @Transactional
    public ScheduledPayment schedulePayment(SchedulePaymentRequestDTO request, String accountId) {
        ScheduledPayment payment = new ScheduledPayment(
                accountId,
                request.amount(),
                request.scheduledDate(),
                request.description(),
                PaymentStatus.PENDING
        );

        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public List<ScheduledPayment> getScheduledPayments(String accountId) {
        return paymentRepository.findByAccountIdAndStatus(accountId, PaymentStatus.PENDING);
    }

    @Scheduled(fixedRate = 60000) // Check every minute
    @Transactional
    public void processScheduledPayments() {
        LOGGER.info("Processing scheduled payments");
        LocalDateTime now = LocalDateTime.now();
        LOGGER.info("Current time: {}", now);

        List<ScheduledPayment> duePayments = paymentRepository
                .findByStatusAndScheduledDateBefore(PaymentStatus.PENDING, now);

        LOGGER.info("Found {} payments to process", duePayments.size());

        for (ScheduledPayment payment : duePayments) {
            LOGGER.info("Processing payment: ID={}, scheduledDate={}, amount={}",
                    payment.getId(), payment.getScheduledDate(), payment.getAmount());
            try {
                accountService.withdraw(payment.getAccountId(), payment.getAmount());
                payment = new ScheduledPayment(
                        payment.getAccountId(),
                        payment.getAmount(),
                        payment.getScheduledDate(),
                        payment.getDescription(),
                        PaymentStatus.COMPLETED
                );
                LOGGER.info("Payment completed successfully: {}", payment.getId());
            } catch (Exception e) {
                LOGGER.error("Failed to process payment: {}", e.getMessage());
                payment = new ScheduledPayment(
                        payment.getAccountId(),
                        payment.getAmount(),
                        payment.getScheduledDate(),
                        payment.getDescription(),
                        PaymentStatus.FAILED
                );
            }
            paymentRepository.save(payment);
        }
    }
}