package com.banking.service;

import com.banking.domain.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger LOGGER = LogManager.getLogger(KafkaConsumerService.class);

    @KafkaListener(
            topics = "banking.transactions",
            groupId = "banking-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeTransaction(Transaction transaction) {
        LOGGER.info("Received transaction: {}", transaction);
        // Add any additional processing logic here
    }
}