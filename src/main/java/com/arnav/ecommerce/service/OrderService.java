package com.arnav.ecommerce.service;

import com.arnav.ecommerce.model.*;
import com.arnav.ecommerce.repository.OrderRepository;
import com.arnav.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RecommendationServiceWrapper recommendationService;

    @Transactional
    public Order checkout(User user) {
        Cart cart = cartService.getCartByUser(user);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot checkout an empty cart");
        }

        Order order = new Order();
        order.setUser(user);

        double total = 0.0;
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            order.getItems().add(orderItem);

            total += product.getPrice() * cartItem.getQuantity();

            product.setPopularity(product.getPopularity() + cartItem.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(total);
        order.setStatus(Order.Status.PAID);
        Order savedOrder = orderRepository.save(order);

        List<Product> productsInOrder = order.getItems().stream()
                .map(OrderItem::getProduct)
                .distinct()
                .toList();

        for (int i = 0; i < productsInOrder.size(); i++) {
            for (int j = i + 1; j < productsInOrder.size(); j++) {
                recommendationService.recordCoPurchase(
                        productsInOrder.get(i).getId(),
                        productsInOrder.get(j).getId()
                );
            }
        }

        cart.getItems().clear();

        return savedOrder;
    }

    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}