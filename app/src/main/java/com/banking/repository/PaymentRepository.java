package com.banking.repository;

import com.banking.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.nextDueDate >= :today AND p.status = 'ACTIVE' ORDER BY p.nextDueDate ASC")
    List<Payment> findUpcomingPayments(LocalDate today, int limit);
}