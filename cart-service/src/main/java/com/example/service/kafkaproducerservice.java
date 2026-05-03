package com.example.service;
import com.example.dto.cartevent;
import org.jboss.logging.BasicLogger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service

public class kafkaproducerservice {
    private final KafkaTemplate<String, cartevent> kafkaTemplate;
    private static final String TOPIC = "cart-topic";
    public kafkaproducerservice(KafkaTemplate<String, cartevent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendCartEvent(cartevent event) {
        kafkaTemplate.send(TOPIC, event);
//        Log.info("Sent cart event to kafka: " + event);

        log.info("Kafka event sent: {}", event);
    }
}
