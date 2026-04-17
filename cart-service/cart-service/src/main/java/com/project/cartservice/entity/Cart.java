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
    
    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

   
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
      
}