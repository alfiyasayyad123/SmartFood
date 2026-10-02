package com.smartfood.backend.config;

import com.smartfood.backend.entity.Food;
import com.smartfood.backend.entity.Restaurant;
import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.FoodRepository;
import com.smartfood.backend.repository.RestaurantRepository;
import com.smartfood.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            FoodRepository foodRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        seedAdmin();
        seedRestaurantAndFoods();
    }

    // =========================================================
    // ADMIN
    // =========================================================

    private void seedAdmin() {

        String adminEmail = "admin@smartfood.com";

        boolean exists =
                userRepository.existsByEmail(adminEmail);

        if (!exists) {

            User admin = new User();

            admin.setFullName("SmartFood Admin");
            admin.setEmail(adminEmail);

            admin.setPassword(
                    passwordEncoder.encode("Admin@123")
            );

            admin.setRole("ADMIN");

            userRepository.save(admin);

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "SMARTFOOD ADMIN CREATED"
            );

            System.out.println(
                    "Email: admin@smartfood.com"
            );

            System.out.println(
                    "Password: Admin@123"
            );

            System.out.println(
                    "Role: ADMIN"
            );

            System.out.println(
                    "=========================================="
            );

        } else {

            System.out.println(
                    "SmartFood Admin already exists."
            );
        }
    }

    // =========================================================
    // RESTAURANT + FOOD
    // =========================================================

    private void seedRestaurantAndFoods() {

        Restaurant restaurant;

        List<Restaurant> existingRestaurants =
                restaurantRepository
                        .findByNameContainingIgnoreCase(
                                "Urban Pizza House"
                        );

        if (existingRestaurants.isEmpty()) {

            restaurant = new Restaurant();

            restaurant.setName(
                    "Urban Pizza House"
            );

            restaurant.setDescription(
                    "Pizza • Italian • Fast Food"
            );

            restaurant.setAddress(
                    "Civil Lines, Nagpur"
            );

            restaurant.setCity(
                    "Nagpur"
            );

            restaurant.setPhone(
                    null
            );

            restaurant.setImageUrl(
                    "https://images.unsplash.com/photo-1579751626657-72bc17010498?auto=format&fit=crop&w=1200&q=80"
            );

            restaurant.setActive(true);

            restaurant =
                    restaurantRepository.save(
                            restaurant
                    );

            System.out.println(
                    "Urban Pizza House created."
            );

        } else {

            restaurant =
                    existingRestaurants.get(0);

            System.out.println(
                    "Urban Pizza House already exists."
            );
        }

        seedFoods(restaurant);
    }

    // =========================================================
    // FOOD ITEMS
    // =========================================================

    private void seedFoods(
            Restaurant restaurant
    ) {

        List<Food> existingFoods =
                foodRepository.findByRestaurantId(
                        restaurant.getId()
                );

        Set<String> existingFoodNames =
                existingFoods.stream()
                        .map(food ->
                                food.getName()
                        )
                        .collect(
                                Collectors.toSet()
                        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Margherita Pizza",
                "Classic pizza topped with tomato, mozzarella and basil.",
                249,
                "Pizza",
                "https://images.unsplash.com/photo-1579751626657-72bc17010498?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Farmhouse Pizza",
                "Loaded with onion, capsicum, tomato and delicious cheese.",
                299,
                "Pizza",
                "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Chicken Tikka Pizza",
                "Spicy chicken tikka pizza with mozzarella cheese.",
                349,
                "Pizza",
                "https://images.unsplash.com/photo-1593560708920-61dd98c46a4e?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Pepperoni Pizza",
                "Cheesy pizza topped with premium pepperoni.",
                379,
                "Pizza",
                "https://images.unsplash.com/photo-1628840042765-356cda07504e?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Paneer Pizza",
                "Indian-style paneer pizza with onion and capsicum.",
                319,
                "Pizza",
                "https://images.unsplash.com/photo-1579751626657-72bc17010498?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Cheese Burst Pizza",
                "Extra cheesy pizza with rich molten cheese filling.",
                399,
                "Pizza",
                "https://images.unsplash.com/photo-1579751626657-72bc17010498?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Classic Cheese Burger",
                "Soft bun with crispy lettuce, tomato and cheese.",
                199,
                "Burger",
                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Chicken Burger",
                "Juicy chicken patty burger with fresh vegetables.",
                249,
                "Burger",
                "https://images.unsplash.com/photo-1571091718767-18b5b1457add?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Double Cheese Burger",
                "Double patty burger loaded with extra cheese.",
                299,
                "Burger",
                "https://images.unsplash.com/photo-1586190848861-99aa4a171e90?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Mexican Burger",
                "Spicy Mexican burger with jalapenos and cheese.",
                279,
                "Burger",
                "https://images.unsplash.com/photo-1550547660-d9450f859349?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Crispy Chicken Burger",
                "Crunchy crispy chicken burger with creamy sauce.",
                289,
                "Burger",
                "https://images.unsplash.com/photo-1610970881699-44a5587cabec?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "French Fries",
                "Golden crispy French fries.",
                129,
                "Sides",
                "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Peri Peri Fries",
                "Crispy fries tossed in spicy peri peri seasoning.",
                159,
                "Sides",
                "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Garlic Bread",
                "Soft garlic bread with herbs and butter.",
                149,
                "Sides",
                "https://images.unsplash.com/photo-1573140247632-f8fd74997d5c?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Cheese Garlic Bread",
                "Garlic bread loaded with melted cheese.",
                179,
                "Sides",
                "https://images.unsplash.com/photo-1573140247632-f8fd74997d5c?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Cheese Balls",
                "Crispy cheese-filled snack bites.",
                169,
                "Sides",
                "https://images.unsplash.com/photo-1548340748-6d2b7d7a7e9a?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Creamy Alfredo Pasta",
                "Creamy white sauce pasta with herbs and cheese.",
                269,
                "Pasta",
                "https://images.unsplash.com/photo-1645112411341-6c4fd023714a?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Arrabbiata Pasta",
                "Spicy tomato-based Italian pasta.",
                249,
                "Pasta",
                "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Pesto Pasta",
                "Creamy pesto pasta with basil and parmesan.",
                289,
                "Pasta",
                "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Cheese Pasta",
                "Cheesy pasta with rich creamy sauce.",
                259,
                "Pasta",
                "https://images.unsplash.com/photo-1555949258-eb67b1ef0ceb?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Veggie Pasta",
                "Fresh vegetables tossed with delicious pasta.",
                239,
                "Pasta",
                "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Chocolate Cake",
                "Rich and soft chocolate cake.",
                149,
                "Dessert",
                "https://images.unsplash.com/photo-1578985545062-69928b1d9587?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Red Velvet Cake",
                "Soft red velvet cake with creamy frosting.",
                179,
                "Dessert",
                "https://images.unsplash.com/photo-1586788224331-947f68671cf1?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Brownie",
                "Warm fudgy chocolate brownie.",
                119,
                "Dessert",
                "https://images.unsplash.com/photo-1564355808539-22fda35bed7d?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Ice Cream Sundae",
                "Vanilla ice cream topped with chocolate sauce.",
                159,
                "Dessert",
                "https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Chocolate Donut",
                "Soft donut coated with rich chocolate.",
                99,
                "Dessert",
                "https://images.unsplash.com/photo-1551024506-0bccd828d307?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Cold Coffee",
                "Chilled creamy cold coffee.",
                139,
                "Drink",
                "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Chocolate Shake",
                "Thick chocolate milkshake.",
                159,
                "Drink",
                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Strawberry Shake",
                "Creamy strawberry milkshake.",
                159,
                "Drink",
                "https://images.unsplash.com/photo-1553787499-6f9133860278?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Mango Shake",
                "Fresh mango milkshake.",
                149,
                "Drink",
                "https://images.unsplash.com/photo-1546173159-315724a31696?auto=format&fit=crop&w=600&q=80"
        );

        addFoodIfMissing(
                restaurant,
                existingFoodNames,
                "Fresh Lime Soda",
                "Refreshing sweet and salty lime soda.",
                99,
                "Drink",
                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?auto=format&fit=crop&w=600&q=80"
        );

        System.out.println(
                "SmartFood restaurant and food seeding completed."
        );
    }

    // =========================================================
    // ADD FOOD IF NOT EXISTS
    // =========================================================

    private void addFoodIfMissing(
            Restaurant restaurant,
            Set<String> existingFoodNames,
            String name,
            String description,
            double price,
            String category,
            String imageUrl
    ) {

        if (existingFoodNames.contains(name)) {
            return;
        }

        Food food = new Food();

        food.setName(name);
        food.setDescription(description);
        food.setPrice(price);
        food.setCategory(category);
        food.setImageUrl(imageUrl);
        food.setAvailable(true);
        food.setRestaurant(restaurant);

        foodRepository.save(food);

        existingFoodNames.add(name);
    }
}