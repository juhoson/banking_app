package com.banking.controller;

import com.banking.domain.TimeFrame;
import com.banking.domain.TrendType;
import com.banking.domain.dto.BalanceChangeDTO;
import com.banking.domain.dto.DashboardStatsDTO;
import com.banking.domain.dto.ExpenseCategoryDTO;
import com.banking.domain.dto.UpcomingPaymentDTO;
import com.banking.repository.PaymentRepository;
import com.banking.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;

    @GetMapping("/stats")
    public DashboardStatsDTO getDashboardStats() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        double currentBalance = transactionRepository.getCurrentBalance();
        double previousBalance = transactionRepository.getBalanceAsOf(startOfMonth.minusDays(1));
        double monthlyIncome = transactionRepository.getTotalIncomeForPeriod(startOfMonth, endOfMonth);
        double monthlyExpenses = transactionRepository.getTotalExpensesForPeriod(startOfMonth, endOfMonth);

        // Calculate balance change percentage and trend
        double balanceChange = ((currentBalance - previousBalance) / previousBalance) * 100;
        TrendType trend = balanceChange >= 0 ? TrendType.INCREASE : TrendType.DECREASE;

        return new DashboardStatsDTO(
                currentBalance,
                monthlyIncome,
                monthlyExpenses,
                10000.0, // TODO: Make this configurable
                currentBalance > 0 ? currentBalance : 0,
                new BalanceChangeDTO(Math.abs(balanceChange), trend)
        );
    }

    @GetMapping("/expense-categories")
    public List<ExpenseCategoryDTO> getExpenseCategories(
            @RequestParam(defaultValue = "MONTH") TimeFrame timeframe
    ) {
        LocalDate startDate = switch (timeframe) {
            case WEEK -> LocalDate.now().minusWeeks(1);
            case MONTH -> LocalDate.now().minusMonths(1);
            case YEAR -> LocalDate.now().minusYears(1);
        };

        double totalExpenses = transactionRepository.getTotalExpensesForPeriod(startDate, LocalDate.now());

        return transactionRepository.getExpensesByCategory(startDate, LocalDate.now())
                .stream()
                .map(expense -> new ExpenseCategoryDTO(
                        expense.getCategory(),
                        expense.getAmount(),
                        (expense.getAmount() / totalExpenses) * 100,
                        getCategoryColor(expense.getCategory())
                ))
                .toList();
    }

    @GetMapping("/upcoming-payments")
    public List<UpcomingPaymentDTO> getUpcomingPayments(
            @RequestParam(defaultValue = "5") int limit
    ) {
        LocalDate today = LocalDate.now();

        return paymentRepository.findUpcomingPayments(today, limit)
                .stream()
                .map(payment -> new UpcomingPaymentDTO(
                        payment.getId(),
                        payment.getName(),
                        payment.getAmount(),
                        payment.getNextDueDate(),
                        today.until(payment.getNextDueDate()).getDays()
                ))
                .toList();
    }

    private String getCategoryColor(String category) {
        return switch (category.toLowerCase()) {
            case "food" -> "green";
            case "transportation" -> "blue";
            case "entertainment" -> "purple";
            case "utilities" -> "orange";
            default -> "gray";
        };
    }
}

