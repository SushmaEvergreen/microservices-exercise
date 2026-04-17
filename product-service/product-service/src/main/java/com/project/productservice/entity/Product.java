package com.project.productservice.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private Double price;
	private Integer stock;
	
	public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setId(Integer id) {
        this.id = id;
    }
	
}

	
