package com.project.cartservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cartservice.entity.Cart;
import com.project.cartservice.entity.CartItem;
import com.project.cartservice.repository.CartRepository;
import com.project.cartservice.repository.CartItemRepository;
import com.project.cartservice.client.ProductClient;
import com.project.cartservice.dto.ProductDto;
import com.project.cartservice.event.CartEvent;
import com.project.cartservice.producer.CartProducer;

@Service
public class CartService {
	
	private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartProducer cartProducer;

    // Custom thread pool (real-world best practice)
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    
     
    // CREATE CART
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    // ADD ITEM TO CART (ASYNC IMPLEMENTATION)
    public CartItem addItem(CartItem item) {

        try {
        	
        	logger.info("Add item API called with productId: {}, quantity: {}", 
                    item.getProductId(), item.getQuantity());
        	
        	// STEP 0: Input Validation
        	if (item.getQuantity() == null) {
        		logger.error("Validation failed: Quantity is null");
        	    throw new IllegalArgumentException("Quantity cannot be null");
        	}

        	if (item.getQuantity() <= 0) {
        		logger.error("Validation failed: Quantity must be greater than 0");
        	    throw new IllegalArgumentException("Quantity must be greater than 0");
        	}
        	
        	logger.info("Validation successful for productId: {}", item.getProductId());
        	
            // STEP 1: Fetch product asynchronously
        	CompletableFuture<ProductDto> productFuture =
                    CompletableFuture.supplyAsync(() -> {
                        
                    	//This is used to verify async execution
                       // System.out.println("FETCH Thread: " + Thread.currentThread().getName());

                        return productClient.getProductById(item.getProductId().longValue());

                    }, executor);

            // STEP 2: Validate  asynchronously
            CompletableFuture<ProductDto> validatedFuture =
                    productFuture.thenApplyAsync(product -> {

                    	//System.out.println("VALIDATION Thread: " + Thread.currentThread().getName());

                    	
                        if (product == null) {
                            throw new RuntimeException("Product not found");
                        }

                        if (product.getStock() < item.getQuantity()) {
                            throw new RuntimeException("Insufficient stock");
                        }

                        return product;

                    }, executor);

            // STEP 3: Wait only once (non-blocking until here)
            validatedFuture.join();
            
            logger.info("Async product fetch and validation completed");

            // STEP 4: Save item
            CartItem savedItem = cartItemRepository.save(item);
            
            logger.info("Item saved to DB with id: {}", savedItem.getId());

            // STEP 5: Create Kafka Event
            CartEvent event = new CartEvent(
                    savedItem.getId(),
                    savedItem.getProductId(),
                    savedItem.getQuantity()
            );

            // STEP 6: Send Event to Kafka
            cartProducer.sendEvent(event);
            
            logger.info("Event sent to Kafka for cartId: {}", savedItem.getId());

            return savedItem;

        } 
        catch (Exception e) {
        	logger.error("Error while adding item", e);
        	throw e;
        }
    }

    // GET ALL CART ITEMS
    public List<CartItem> getAllItems() {
        return cartItemRepository.findAll();
    }

    // DELETE ITEM
    public void deleteItem(Integer id) {
        cartItemRepository.deleteById(id);
    }

    // GET ALL CARTS
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}