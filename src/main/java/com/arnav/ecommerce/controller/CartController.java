package com.arnav.ecommerce.controller;

import com.arnav.ecommerce.model.Cart;
import com.arnav.ecommerce.model.User;
import com.arnav.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    public record AddItemRequest(Long productId, Integer quantity) {}

    @GetMapping
    public Cart getCart(@AuthenticationPrincipal User user) {
        return cartService.getCartByUser(user);
    }

    @PostMapping("/items")
    public Cart addItem(@AuthenticationPrincipal User user, @RequestBody AddItemRequest req) {
        return cartService.addItemToCart(user, req.productId(), req.quantity());
    }

    @DeleteMapping("/items/{itemId}")
    public Cart removeItem(@AuthenticationPrincipal User user, @PathVariable Long itemId) {
        return cartService.removeItemFromCart(user, itemId);
    }
}