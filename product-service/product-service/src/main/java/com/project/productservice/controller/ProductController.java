package com.project.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.productservice.entity.Product;
import com.project.productservice.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 🔹 CREATE PRODUCT
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    
    // 🔹 GET ALL PRODUCTS
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 🔹 GET PRODUCT BY ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    // 🔹 UPDATE PRODUCT
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        product.setId(id);
        return productService.createProduct(product);
    }

    // 🔹 DELETE PRODUCT
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}