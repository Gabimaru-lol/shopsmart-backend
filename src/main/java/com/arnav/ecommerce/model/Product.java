package com.arnav.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    private Double rating = 0.0;

    private String imageUrl;

    private Integer popularity = 0;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}