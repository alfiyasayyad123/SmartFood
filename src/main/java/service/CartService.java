package com.smartfood.backend.service;

import com.smartfood.backend.entity.Cart;
import com.smartfood.backend.entity.Food;
import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.CartRepository;
import com.smartfood.backend.repository.FoodRepository;
import com.smartfood.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    public CartService(
            CartRepository cartRepository,
            UserRepository userRepository,
            FoodRepository foodRepository) {

        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
    }

    @Transactional
    public Cart addToCart(
            Long userId,
            Long foodId,
            int quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() ->
                        new RuntimeException("Food not found"));

        if (!food.isAvailable()) {
            throw new RuntimeException(
                    "Food is currently unavailable");
        }

        if (quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than zero");
        }

        Cart cart = cartRepository
                .findByUserIdAndFoodId(userId, foodId)
                .orElse(null);

        if (cart != null) {

            cart.setQuantity(
                    cart.getQuantity() + quantity
            );

        } else {

            cart = new Cart();

            cart.setUser(user);
            cart.setFood(food);
            cart.setQuantity(quantity);
        }

        // Force INSERT/UPDATE immediately in database
        return cartRepository.saveAndFlush(cart);
    }

    public List<Cart> getUserCart(Long userId) {

        return cartRepository.findByUserId(userId);
    }

    @Transactional
    public Cart updateQuantity(
            Long cartId,
            int quantity) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart item not found"));

        if (quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than zero");
        }

        cart.setQuantity(quantity);

        return cartRepository.saveAndFlush(cart);
    }

    @Transactional
    public void removeFromCart(Long cartId) {

        if (!cartRepository.existsById(cartId)) {
            throw new RuntimeException(
                    "Cart item not found");
        }

        cartRepository.deleteById(cartId);
    }

    @Transactional
    public void clearCart(Long userId) {

        cartRepository.deleteByUserId(userId);
    }

    public double calculateTotal(Long userId) {

        List<Cart> cartItems =
                cartRepository.findByUserId(userId);

        double total = 0;

        for (Cart cart : cartItems) {

            total += cart.getFood().getPrice()
                    * cart.getQuantity();
        }

        return total;
    }
}