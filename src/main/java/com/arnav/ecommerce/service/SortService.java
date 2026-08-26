package com.arnav.ecommerce.service;

import com.arnav.ecommerce.dsa.ProductSorter;
import com.arnav.ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SortService {

    public List<Product> sortProducts(List<Product> products, String sortBy, String order) {
        Comparator<Product> comparator = switch (sortBy.toLowerCase()) {
            case "price" -> Comparator.comparing(Product::getPrice);
            case "rating" -> Comparator.comparing(Product::getRating);
            case "popularity" -> Comparator.comparing(Product::getPopularity);
            default -> Comparator.comparing(Product::getId);
        };

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        ProductSorter.sort(products, comparator);
        return products;
    }
}