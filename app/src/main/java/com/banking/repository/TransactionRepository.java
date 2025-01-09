package com.banking.repository;

import com.banking.domain.CategoryExpense;
import com.banking.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdOrderByTimestampDesc(String accountId);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE -t.amount END), 0) FROM Transaction t")
    double getCurrentBalance();

    @Query("SELECT COALESCE(SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE -t.amount END), 0) FROM Transaction t WHERE t.timestamp <= :date")
    double getBalanceAsOf(LocalDate date);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'CREDIT' AND t.timestamp BETWEEN :startDate AND :endDate")
    double getTotalIncomeForPeriod(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'DEBIT' AND t.timestamp BETWEEN :startDate AND :endDate")
    double getTotalExpensesForPeriod(LocalDate startDate, LocalDate endDate);

    @Query("SELECT new CategoryExpense(t.category, SUM(t.amount)) " +
            "FROM Transaction t " +
            "WHERE t.type = 'DEBIT' AND t.timestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY t.category")
    List<CategoryExpense> getExpensesByCategory(LocalDate startDate, LocalDate endDate);
}