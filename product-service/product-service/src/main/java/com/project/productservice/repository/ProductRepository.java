package com.project.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}