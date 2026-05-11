package com.project.cartservice.service;

import java.util.List;

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

	
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductClient productClient;
    
    @Autowired //Injecting Kafka Producer here //
    private CartProducer cartProducer;

    // CREATE CART
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    // ADD ITEM TO CART
    
    public CartItem addItem(CartItem item) {

       /** // Step 1: Call Product Service
        ProductDto product = productClient.getProductById(item.getProductId());**/
        
        ProductDto product = productClient.getProductById(item.getProductId().longValue());

        //Validation 1: Product exists
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        //Validation 2: Stock check
        if (product.getStock() < item.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }
        
        
        //Step 2 : Save item
        CartItem savedItem = cartItemRepository.save(item);
        
     //Step 3: Create Kafka Event
        CartEvent event = new CartEvent(
                savedItem.getId(),
                savedItem.getProductId(),
                savedItem.getQuantity()
        );
        
        
     //Step 4: Send Event to Kafka
        cartProducer.sendEvent(event);
        
       /** try {
            cartProducer.sendEvent(event);
        } catch (Exception e) {
            System.out.println("Kafka not available, skipping event...");
        }**/
        
        // Return saved item
        return savedItem;
     
    }  

       /** //Save only if valid
        return cartItemRepository.save(item);
    }**/
    
    
  /**  public CartItem addItem(CartItem item) {
        return cartItemRepository.save(item);
    }*/

    // GET ALL CART ITEMS
    public List<CartItem> getAllItems() {
        return cartItemRepository.findAll();
    }

 //DELETE ITEM 
    public void deleteItem(Integer id) {
        cartItemRepository.deleteById(id);
    }

    //GET ALL CARTS 
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}

