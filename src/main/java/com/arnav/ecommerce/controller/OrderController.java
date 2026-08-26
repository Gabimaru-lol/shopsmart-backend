package com.arnav.ecommerce.controller;

import com.arnav.ecommerce.model.Order;
import com.arnav.ecommerce.model.User;
import com.arnav.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order checkout(@AuthenticationPrincipal User user) {
        return orderService.checkout(user);
    }

    @GetMapping
    public List<Order> getMyOrders(@AuthenticationPrincipal User user) {
        return orderService.getOrdersForUser(user.getId());
    }
}