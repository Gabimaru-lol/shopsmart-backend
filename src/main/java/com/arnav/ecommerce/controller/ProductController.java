package com.arnav.ecommerce.controller;

import com.arnav.ecommerce.model.Product;
import com.arnav.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String q) {
        return productService.searchProducts(q);
    }

    @GetMapping("/sort")
    public List<Product> sortProducts(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        return productService.sortProducts(sortBy, order);
    }

    @GetMapping("/{id}/recommendations")
    public List<Product> getRecommendations(
            @PathVariable Long id,
            @RequestParam(defaultValue = "5") int topN) {
        return productService.getRecommendations(id, topN);
    }
}