package com.project.cartservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
}