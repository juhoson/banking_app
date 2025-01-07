package com.banking.repository;

import com.banking.domain.ScheduledPayment;
import com.banking.domain.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPayment, Long> {
    List<ScheduledPayment> findByAccountId(String accountId);
    
    List<ScheduledPayment> findByStatusAndScheduledDateBefore(
        PaymentStatus status, 
        LocalDateTime date
    );
    
    List<ScheduledPayment> findByAccountIdAndStatus(
        String accountId, 
        PaymentStatus status
    );
}