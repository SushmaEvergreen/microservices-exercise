package com.project.productservice.dto;

public class ProductDTO {

    private Integer id;
    private String name;
    private double price;
    private int stock;

    public ProductDTO(Integer id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
}