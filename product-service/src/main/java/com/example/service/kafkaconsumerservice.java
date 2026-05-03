
package com.example.service;

import com.example.dto.cartevent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class kafkaconsumerservice {

    @KafkaListener(
            topics = "cart-topic",
            groupId = "product-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeCartEvent(cartevent event) {

        log.info("📥 Received cart event: {}", event);
        log.info("CartId: {}", event.getCartId());
        log.info("ProductId: {}", event.getProductId());
        log.info("Quantity: {}", event.getQuantity());

        // TODO: update inventory here

        log.info("✅ Cart event processed successfully");
    }
}
