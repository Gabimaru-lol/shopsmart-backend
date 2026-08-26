package com.arnav.ecommerce.service;

import com.arnav.ecommerce.dsa.Trie;
import com.arnav.ecommerce.model.Product;
import com.arnav.ecommerce.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private ProductRepository productRepository;

    private final Trie trie = new Trie();

    @PostConstruct
    public void buildTrie() {
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            trie.insert(product.getName(), product.getId());
        }
    }

    public void addProductToTrie(Product product) {
        trie.insert(product.getName(), product.getId());
    }

    public List<Product> searchByPrefix(String prefix) {
        List<Long> matchingIds = trie.searchByPrefix(prefix);
        return matchingIds.stream()
                .map(id -> productRepository.findById(id).orElse(null))
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }
}