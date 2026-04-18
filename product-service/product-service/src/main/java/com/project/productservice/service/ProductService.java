package com.project.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.productservice.entity.Product;
import com.project.productservice.repository.ProductRepository;

import com.project.productservice.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // CREATE PRODUCT
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // GET ALL PRODUCTS (OLD - keep if needed)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //PAGINATION + SORTING + STREAMS (MAIN METHOD)
    public Page<ProductDTO> getProducts(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAll(pageable);

        //Java Streams (Entity → DTO)
        return productPage.map(p -> new ProductDTO(p.getName(), p.getPrice()));
    }

    // GET PRODUCT BY ID
    public Product getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    // VALIDATE STOCK
    public boolean isStockAvailable(Integer productId, Integer quantity) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(p -> p.getStock() >= quantity).orElse(false);
    }

    // DELETE PRODUCT
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
    
 // FILTER PRODUCTS USING JAVA STREAMS
    public List<ProductDTO> getFilteredProducts(double minPrice) {

        return productRepository.findAll()
                .stream()
                .filter(p -> p.getPrice() >= minPrice)
                .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice())) // sorting added
                .map(p -> new ProductDTO(p.getName(), p.getPrice()))
                .toList();
    }
    
}

