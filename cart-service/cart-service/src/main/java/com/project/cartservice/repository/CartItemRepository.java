package com.project.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.cartservice.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}