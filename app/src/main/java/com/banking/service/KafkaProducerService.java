package com.banking.service;

import com.banking.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, Transaction> kafkaTemplate;
    private static final String TRANSACTION_TOPIC = "banking.transactions";

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(Transaction transaction) {
        kafkaTemplate.send(TRANSACTION_TOPIC, transaction.getAccountId(), transaction);
    }
}