package com.arnav.ecommerce.dsa;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendationService {

    private final Map<Long, List<Edge>> graph = new HashMap<>();

    public record Edge(Long neighborId, int weight) {}

    public void addEdge(Long productA, Long productB, int weight) {
        graph.computeIfAbsent(productA, k -> new ArrayList<>()).add(new Edge(productB, weight));
        graph.computeIfAbsent(productB, k -> new ArrayList<>()).add(new Edge(productA, weight));
    }

    public void recordCoPurchase(Long productA, Long productB) {
        updateOrInsertEdge(productA, productB);
        updateOrInsertEdge(productB, productA);
    }

    private void updateOrInsertEdge(Long from, Long to) {
        List<Edge> edges = graph.computeIfAbsent(from, k -> new ArrayList<>());
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).neighborId().equals(to)) {
                Edge updated = new Edge(to, edges.get(i).weight() + 1);
                edges.set(i, updated);
                return;
            }
        }
        edges.add(new Edge(to, 1));
    }

    public List<Long> getTopRecommendations(Long productId, int topN) {
        List<Edge> edges = graph.getOrDefault(productId, Collections.emptyList());

        return edges.stream()
                .sorted((e1, e2) -> Integer.compare(e2.weight(), e1.weight()))
                .limit(topN)
                .map(Edge::neighborId)
                .collect(Collectors.toList());
    }
}