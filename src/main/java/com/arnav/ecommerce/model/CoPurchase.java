package com.arnav.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "co_purchases")
@Data
public class CoPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productIdA;

    @Column(nullable = false)
    private Long productIdB;

    @Column(nullable = false)
    private Integer weight = 1;
}