package com.perfulandia.gestiondeinventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =  "Producto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable=false)  
    private String name;

    @Column(nullable=false)  
    private String description;

    @Column(nullable=true)  
    private double price;

    @Column(nullable=false)  
    private int stockQuantity;

    @Column(nullable = false)
    private String category;

}
