package com.project.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.cartservice.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
