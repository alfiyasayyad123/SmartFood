package com.smartfood.backend.repository;

import com.smartfood.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

    Optional<Cart> findByUserIdAndFoodId(Long userId, Long foodId);

    void deleteByUserId(Long userId);
}