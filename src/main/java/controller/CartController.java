package com.smartfood.backend.controller;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.entity.Cart;
import com.smartfood.backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add food to cart
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Cart>> addToCart(
            @RequestParam Long userId,
            @RequestParam Long foodId,
            @RequestParam int quantity) {

        Cart cart = cartService.addToCart(
                userId,
                foodId,
                quantity
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Food added to cart successfully",
                        cart
                )
        );
    }

    // Get user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<Cart>>> getUserCart(
            @PathVariable Long userId) {

        List<Cart> cartItems =
                cartService.getUserCart(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart fetched successfully",
                        cartItems
                )
        );
    }

    // Update quantity
    @PutMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Cart>> updateQuantity(
            @PathVariable Long cartId,
            @RequestParam int quantity) {

        Cart cart = cartService.updateQuantity(
                cartId,
                quantity
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart quantity updated successfully",
                        cart
                )
        );
    }

    // Remove item
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<String>> removeFromCart(
            @PathVariable Long cartId) {

        cartService.removeFromCart(cartId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Item removed from cart successfully",
                        null
                )
        );
    }

    // Clear cart
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<ApiResponse<String>> clearCart(
            @PathVariable Long userId) {

        cartService.clearCart(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart cleared successfully",
                        null
                )
        );
    }

    // Calculate total
    @GetMapping("/{userId}/total")
    public ResponseEntity<ApiResponse<Double>> calculateTotal(
            @PathVariable Long userId) {

        double total =
                cartService.calculateTotal(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cart total calculated successfully",
                        total
                )
        );
    }
}