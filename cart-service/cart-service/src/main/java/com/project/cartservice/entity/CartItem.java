package com.project.cartservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cart_item")
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    

    public Integer getId() {
        return id;
    }

    public Integer getCartId() {
        return cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

   
    public void setId(Integer id) {
        this.id = id;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
    
    