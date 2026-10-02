package com.smartfood.backend.repository;

import com.smartfood.backend.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByRestaurantId(Long restaurantId);

    List<Food> findByCategoryIgnoreCase(String category);

    List<Food> findByNameContainingIgnoreCase(String name);

    List<Food> findByAvailableTrue();

    List<Food> findByRestaurantIdAndAvailableTrue(Long restaurantId);
}