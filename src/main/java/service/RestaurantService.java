package com.smartfood.backend.service;

import com.smartfood.backend.entity.Restaurant;
import com.smartfood.backend.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getActiveRestaurants() {
        return restaurantRepository.findByActiveTrue();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found with id: " + id));
    }

    public List<Restaurant> searchRestaurants(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Restaurant> getRestaurantsByCity(String city) {
        return restaurantRepository.findByCityIgnoreCase(city);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {

        Restaurant existingRestaurant = getRestaurantById(id);

        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setDescription(updatedRestaurant.getDescription());
        existingRestaurant.setAddress(updatedRestaurant.getAddress());
        existingRestaurant.setCity(updatedRestaurant.getCity());
        existingRestaurant.setPhone(updatedRestaurant.getPhone());
        existingRestaurant.setImageUrl(updatedRestaurant.getImageUrl());
        existingRestaurant.setActive(updatedRestaurant.isActive());

        return restaurantRepository.save(existingRestaurant);
    }

    public void deleteRestaurant(Long id) {

        if (!restaurantRepository.existsById(id)) {
            throw new RuntimeException(
                    "Restaurant not found with id: " + id
            );
        }

        restaurantRepository.deleteById(id);
    }
}