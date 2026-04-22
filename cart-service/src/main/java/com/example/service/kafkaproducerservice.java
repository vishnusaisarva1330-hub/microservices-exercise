package com.example.service;
import com.example.dto.cartevent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service

public class kafkaproducerservice {
    private final KafkaTemplate<String, cartevent> kafkaTemplate;
    private static final String TOPIC = "cart-topic";
    public kafkaproducerservice(KafkaTemplate<String, cartevent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendCartEvent(cartevent event) {
        kafkaTemplate.send(TOPIC, event);
        System.out.println("Sent cart event to kafka: " + event);
    }
}
