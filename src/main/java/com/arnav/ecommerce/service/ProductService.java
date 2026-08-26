package com.arnav.ecommerce.service;

import com.arnav.ecommerce.model.Product;
import com.arnav.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SearchService searchService;

    @Autowired
    private SortService sortService;

    @Autowired
    private RecommendationServiceWrapper recommendationService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        Product saved = productRepository.save(product);
        searchService.addProductToTrie(saved);
        return saved;
    }

    public List<Product> searchProducts(String prefix) {
        return searchService.searchByPrefix(prefix);
    }

    public List<Product> sortProducts(String sortBy, String order) {
        List<Product> products = productRepository.findAll();
        return sortService.sortProducts(products, sortBy, order);
    }

    public List<Product> getRecommendations(Long productId, int topN) {
        return recommendationService.getRecommendations(productId, topN);
    }
}