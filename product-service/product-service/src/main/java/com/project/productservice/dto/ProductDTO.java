package com.project.productservice.dto;

public class ProductDTO {

    private String name;
    private double price;

    // Constructor
    public ProductDTO(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }
}
