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
	
}

	
