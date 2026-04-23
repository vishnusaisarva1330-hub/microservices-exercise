//package com.example.service;
//import com.example.dto.cartevent;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//@Service
//
//
//
//public class kafkaconsumerservice {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @KafkaListener(topics = "cart-topic", groupId = "product-group")
//    public void consumeCartEvent(cartevent){
//        try {
////            cartevent event = objectMapper.readValue(message, cartevent.class);
//            System.out.println("Received cart event from kafka: " + event);
//            System.out.println("cartId: "+ event.getCartId());
//            System.out.println("Updating product inventory for product ID: " + event.getProductId());
//            System.out.println(" with quantity: " + event.getQuantity());
//            // Here you can add logic to update product inventory based on the cart event
//        } catch (Exception e) {
//            System.err.println("Failed to consume cart event: " + e.getMessage());
//        }
//    }
//}
package com.example.service;

import com.example.dto.cartevent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class kafkaconsumerservice {

    @KafkaListener(
            topics = "cart-topic",
            groupId = "product-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeCartEvent(cartevent event) {

        System.out.println("✅ Received cart event from Kafka: " + event);
        System.out.println("cartId: " + event.getCartId());
        System.out.println("productId: " + event.getProductId());
        System.out.println("quantity: " + event.getQuantity());

        // TODO: update inventory here
    }
}
