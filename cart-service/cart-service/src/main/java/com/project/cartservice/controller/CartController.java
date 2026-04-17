package com.project.cartservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.cartservice.entity.Cart;
import com.project.cartservice.entity.CartItem;
import com.project.cartservice.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

 //CREATE CART
    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.createCart(cart);
    }

    //GET ALL CARTS  
    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    //ADD ITEM TO CART
    @PostMapping("/items")
    public CartItem addItem(@RequestBody CartItem item) {
        return cartService.addItem(item);
    }

    //GET ALL ITEMS
    @GetMapping("/items")
    public List<CartItem> getItems() {
        return cartService.getAllItems();
    }

    //UPDATE ITEM
    @PutMapping("/items/{id}")
    public CartItem updateItem(@PathVariable Integer id, @RequestBody CartItem item) {
        item.setId(id);
        return cartService.addItem(item);
    }

    //DELETE ITEM
    @DeleteMapping("/items/{id}")
    public String deleteItem(@PathVariable Integer id) {
        cartService.deleteItem(id);
        return "Item deleted successfully";
    }
    
    
}
