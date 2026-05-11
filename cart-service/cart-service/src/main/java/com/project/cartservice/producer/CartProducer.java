package com.project.cartservice.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.project.cartservice.event.CartEvent;

@Service
public class CartProducer {

    @Autowired
    private KafkaTemplate<String, CartEvent> kafkaTemplate;

    private static final String TOPIC = "cart-topic";

    public void sendEvent(CartEvent event) {
        try {
            // Send event and WAIT for response (important)
            kafkaTemplate.send(TOPIC, event).get();

            System.out.println("✅ Event sent to Kafka successfully: " + event);

        } catch (Exception e) {
            // This will now show REAL Kafka error
            System.out.println("❌ REAL ERROR while sending to Kafka: " + e.getMessage());
            e.printStackTrace();
        }
    }
}