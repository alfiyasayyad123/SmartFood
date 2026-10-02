package com.smartfood.backend.controller;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.entity.Restaurant;
import com.smartfood.backend.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Add restaurant
    @PostMapping
    public ResponseEntity<ApiResponse<Restaurant>> addRestaurant(
            @RequestBody Restaurant restaurant) {

        Restaurant savedRestaurant =
                restaurantService.addRestaurant(restaurant);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Restaurant added successfully",
                        savedRestaurant
                )
        );
    }

    // Get all restaurants
    @GetMapping
    public ResponseEntity<ApiResponse<List<Restaurant>>>
    getAllRestaurants() {

        List<Restaurant> restaurants =
                restaurantService.getAllRestaurants();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Restaurants fetched successfully",
                        restaurants
                )
        );
    }

    // Get restaurant by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Restaurant>>
    getRestaurantById(
            @PathVariable Long id) {

        Restaurant restaurant =
                restaurantService.getRestaurantById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Restaurant fetched successfully",
                        restaurant
                )
        );
    }

    // Search restaurants by name
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Restaurant>>>
    searchRestaurants(
            @RequestParam String name) {

        List<Restaurant> restaurants =
                restaurantService.searchRestaurants(name);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Restaurant search completed successfully",
                        restaurants
                )
        );
    }

    // Get restaurants by city
    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<List<Restaurant>>>
    getRestaurantsByCity(
            @PathVariable String city) {

        List<Restaurant> restaurants =
                restaurantService.getRestaurantsByCity(city);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Restaurants fetched successfully",
                        restaurants
                )
        );
    }

    // Update restaurant
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Restaurant>>
    updateRestaurant(
            @PathVariable Long id,
            @RequestBody Restaurant restaurant) {

        Restaurant updatedRestaurant =
                restaurantService.updateRestaurant(
                        id,
                        restaurant
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Restaurant updated successfully",
                        updatedRestaurant
                )
        );
    }

    // Delete restaurant
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>>
    deleteRestaurant(
            @PathVariable Long id) {

        restaurantService.deleteRestaurant(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Restaurant deleted successfully",
                        null
                )
        );
    }
}