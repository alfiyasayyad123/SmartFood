package com.smartfood.backend.service;

import com.smartfood.backend.entity.Food;
import com.smartfood.backend.repository.FoodRepository;
import com.smartfood.backend.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    public FoodService(
            FoodRepository foodRepository,
            RestaurantRepository restaurantRepository) {

        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // Add food to a restaurant
    public Food addFood(Long restaurantId, Food food) {

        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Restaurant not found with id: " + restaurantId));

        food.setRestaurant(restaurant);

        return foodRepository.save(food);
    }

    // Get all food items
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // Get food items of a restaurant
    public List<Food> getFoodsByRestaurant(Long restaurantId) {
        return foodRepository.findByRestaurantId(restaurantId);
    }

    // Get available food items
    public List<Food> getAvailableFoods() {
        return foodRepository.findByAvailableTrue();
    }

    // Get available food of a restaurant
    public List<Food> getAvailableFoodsByRestaurant(Long restaurantId) {
        return foodRepository.findByRestaurantIdAndAvailableTrue(restaurantId);
    }

    // Search food by name
    public List<Food> searchFoods(String name) {
        return foodRepository.findByNameContainingIgnoreCase(name);
    }

    // Search food by category
    public List<Food> getFoodsByCategory(String category) {
        return foodRepository.findByCategoryIgnoreCase(category);
    }

    // Get food by ID
    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Food not found with id: " + id));
    }

    // Update food
    public Food updateFood(Long id, Food updatedFood) {

        Food existingFood = getFoodById(id);

        existingFood.setName(updatedFood.getName());
        existingFood.setDescription(updatedFood.getDescription());
        existingFood.setPrice(updatedFood.getPrice());
        existingFood.setCategory(updatedFood.getCategory());
        existingFood.setImageUrl(updatedFood.getImageUrl());
        existingFood.setAvailable(updatedFood.isAvailable());

        return foodRepository.save(existingFood);
    }

    // Delete food
    public void deleteFood(Long id) {

        if (!foodRepository.existsById(id)) {
            throw new RuntimeException(
                    "Food not found with id: " + id);
        }

        foodRepository.deleteById(id);
    }
}