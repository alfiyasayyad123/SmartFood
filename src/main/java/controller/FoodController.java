package com.smartfood.backend.controller;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.dto.CreateFoodRequest;
import com.smartfood.backend.entity.Food;
import com.smartfood.backend.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // Add food to restaurant
    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse<Food>> addFood(
            @PathVariable Long restaurantId,
            @Valid @RequestBody CreateFoodRequest request) {

        Food food = new Food();

        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setCategory(request.getCategory());
        food.setImageUrl(request.getImageUrl());
        food.setAvailable(request.isAvailable());

        Food savedFood =
                foodService.addFood(restaurantId, food);

        ApiResponse<Food> response =
                new ApiResponse<>(
                        true,
                        "Food added successfully",
                        savedFood
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get all foods
    @GetMapping
    public ResponseEntity<ApiResponse<List<Food>>> getAllFoods() {

        List<Food> foods =
                foodService.getAllFoods();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Foods fetched successfully",
                        foods
                )
        );
    }

    // Get foods by restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse<List<Food>>>
    getFoodsByRestaurant(
            @PathVariable Long restaurantId) {

        List<Food> foods =
                foodService.getFoodsByRestaurant(restaurantId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Restaurant foods fetched successfully",
                        foods
                )
        );
    }

    // Get available foods
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<Food>>>
    getAvailableFoods() {

        List<Food> foods =
                foodService.getAvailableFoods();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Available foods fetched successfully",
                        foods
                )
        );
    }

    // Get available foods by restaurant
    @GetMapping("/restaurant/{restaurantId}/available")
    public ResponseEntity<ApiResponse<List<Food>>>
    getAvailableFoodsByRestaurant(
            @PathVariable Long restaurantId) {

        List<Food> foods =
                foodService.getAvailableFoodsByRestaurant(
                        restaurantId
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Available restaurant foods fetched successfully",
                        foods
                )
        );
    }

    // Search food
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Food>>>
    searchFoods(@RequestParam String name) {

        List<Food> foods =
                foodService.searchFoods(name);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Food search completed successfully",
                        foods
                )
        );
    }

    // Category filter
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Food>>>
    getFoodsByCategory(
            @PathVariable String category) {

        List<Food> foods =
                foodService.getFoodsByCategory(category);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Category foods fetched successfully",
                        foods
                )
        );
    }

    // Get food by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Food>>
    getFoodById(@PathVariable Long id) {

        Food food =
                foodService.getFoodById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Food fetched successfully",
                        food
                )
        );
    }

    // Update food
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Food>>
    updateFood(
            @PathVariable Long id,
            @RequestBody Food food) {

        Food updatedFood =
                foodService.updateFood(id, food);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Food updated successfully",
                        updatedFood
                )
        );
    }

    // Delete food
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>>
    deleteFood(@PathVariable Long id) {

        foodService.deleteFood(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Food deleted successfully",
                        null
                )
        );
    }
}