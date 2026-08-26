package com.arnav.ecommerce.config;

import com.arnav.ecommerce.model.Product;
import com.arnav.ecommerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            return; // already seeded, skip
        }

        productRepository.save(buildProduct(
                "Wireless Mouse",
                "Ergonomic wireless mouse with USB receiver",
                599.00, 50, 4.3,
                "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400"
        ));

        productRepository.save(buildProduct(
                "Mechanical Keyboard",
                "RGB backlit mechanical keyboard with blue switches",
                2499.00, 30, 4.6,
                "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=400"
        ));

        productRepository.save(buildProduct(
                "Laptop Backpack",
                "Water-resistant backpack with padded laptop compartment",
                1299.00, 40, 4.1,
                "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400"
        ));

        productRepository.save(buildProduct(
                "Wireless Headphones",
                "Noise-cancelling over-ear wireless headphones",
                3499.00, 25, 4.5,
                "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400"
        ));

        System.out.println("✅ Seeded 4 starter products into the database.");
    }

    private Product buildProduct(String name, String description, double price,
                                 int stock, double rating, String imageUrl) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setRating(rating);
        product.setImageUrl(imageUrl);
        return product;
    }
}