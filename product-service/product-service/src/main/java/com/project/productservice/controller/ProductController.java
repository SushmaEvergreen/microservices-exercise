package com.project.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import com.project.productservice.entity.Product;
import com.project.productservice.service.ProductService;
import com.project.productservice.dto.ProductDTO;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //CREATE PRODUCT
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    //GET ALL PRODUCTS
   /** @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }**/
    
    
 //PAGINATION + SORTING API
    @GetMapping
    public Page<ProductDTO> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return productService.getProducts(page, size, sortBy, direction);
    }
    
 //FILTERING USING STREAMS
    @GetMapping("/filter")
    public List<ProductDTO> getFilteredProducts(@RequestParam double minPrice) {
        return productService.getFilteredProducts(minPrice);
    }
    
 // NATIVE QUERY API
    @GetMapping("/native")
    public List<ProductDTO> getProductsAbovePrice(@RequestParam double price) {
        return productService.getProductsAbovePrice(price);
    }
    
    
    //GET PRODUCT BY ID
   // @GetMapping("/{id}")
    //public Product getProductById(@PathVariable Integer id) {
        //return productService.getProductById(id);
    //}
    
    @GetMapping("/{id}")
    //public ProductDTO getProductById(@PathVariable Integer id) {//
    
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id){

        Product product = productService.getProductById(id);
         
        if (product == null) {
            //throw new RuntimeException("Product not found");//
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        	
        }
        
        return ResponseEntity.ok(
                new ProductDTO(
        //return new ProductDTO(//
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
                )
        );
    }
    
    //UPDATE PRODUCT
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        product.setId(id);
        return productService.createProduct(product);
    }

    //DELETE PRODUCT
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}
