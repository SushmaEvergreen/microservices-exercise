package com.project.cartservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cartservice.entity.Cart;
import com.project.cartservice.entity.CartItem;
import com.project.cartservice.repository.CartRepository;
import com.project.cartservice.repository.CartItemRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // CREATE CART
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    // ADD ITEM TO CART
    public CartItem addItem(CartItem item) {
        return cartItemRepository.save(item);
    }

    // GET ALL CART ITEMS
    public List<CartItem> getAllItems() {
        return cartItemRepository.findAll();
    }
}
