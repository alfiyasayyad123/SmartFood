package com.smartfood.backend.repository;

import com.smartfood.backend.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCityIgnoreCase(String city);

    List<Restaurant> findByActiveTrue();

    List<Restaurant> findByNameContainingIgnoreCase(String name);
}