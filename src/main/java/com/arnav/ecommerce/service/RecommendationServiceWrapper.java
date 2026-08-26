package com.arnav.ecommerce.service;

import com.arnav.ecommerce.dsa.RecommendationService;
import com.arnav.ecommerce.model.CoPurchase;
import com.arnav.ecommerce.model.Product;
import com.arnav.ecommerce.repository.CoPurchaseRepository;
import com.arnav.ecommerce.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceWrapper {

    @Autowired
    private CoPurchaseRepository coPurchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    private final RecommendationService engine = new RecommendationService();

    @PostConstruct
    public void buildGraph() {
        List<CoPurchase> allEdges = coPurchaseRepository.findAll();
        for (CoPurchase edge : allEdges) {
            engine.addEdge(edge.getProductIdA(), edge.getProductIdB(), edge.getWeight());
        }
    }

    public void recordCoPurchase(Long productA, Long productB) {
        engine.recordCoPurchase(productA, productB);

        Optional<CoPurchase> existing = coPurchaseRepository.findByProductIdAAndProductIdB(productA, productB);
        if (existing.isPresent()) {
            CoPurchase edge = existing.get();
            edge.setWeight(edge.getWeight() + 1);
            coPurchaseRepository.save(edge);
        } else {
            CoPurchase edge = new CoPurchase();
            edge.setProductIdA(productA);
            edge.setProductIdB(productB);
            edge.setWeight(1);
            coPurchaseRepository.save(edge);
        }
    }

    public List<Product> getRecommendations(Long productId, int topN) {
        List<Long> recommendedIds = engine.getTopRecommendations(productId, topN);
        return recommendedIds.stream()
                .map(id -> productRepository.findById(id).orElse(null))
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }
}