package com.yourorg.doctrivia.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "doc-events", groupId = "doc-trivia-group")
    public void listen(String message) {
        System.out.println("Kafka Message Received: " + message);

    }
}
