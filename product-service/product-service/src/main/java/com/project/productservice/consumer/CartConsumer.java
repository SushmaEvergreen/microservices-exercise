
package com.project.productservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.project.productservice.event.CartEvent;

@Service
public class CartConsumer {

    @KafkaListener(topics = "cart-topic", groupId = "product-group")
    public void consume(CartEvent event) {

        System.out.println("====================================");
        System.out.println("📥 EVENT RECEIVED FROM KAFKA");
        System.out.println("Cart ID    : " + event.getCartId());
        System.out.println("Product ID : " + event.getProductId());
        System.out.println("Quantity   : " + event.getQuantity());
        System.out.println("====================================");
    }
}