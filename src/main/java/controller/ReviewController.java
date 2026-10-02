package com.smartfood.backend.controller;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.entity.Review;
import com.smartfood.backend.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Add review
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Review>> addReview(
            @RequestParam Long userId,
            @RequestParam Long restaurantId,
            @RequestParam int rating,
            @RequestParam String comment) {

        Review review = reviewService.addReview(
                userId,
                restaurantId,
                rating,
                comment
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Review added successfully",
                        review
                )
        );
    }

    // Get restaurant reviews
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse<List<Review>>>
    getRestaurantReviews(
            @PathVariable Long restaurantId) {

        List<Review> reviews =
                reviewService.getRestaurantReviews(
                        restaurantId
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Restaurant reviews fetched successfully",
                        reviews
                )
        );
    }

    // Get user's reviews
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Review>>>
    getUserReviews(
            @PathVariable Long userId) {

        List<Review> reviews =
                reviewService.getUserReviews(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User reviews fetched successfully",
                        reviews
                )
        );
    }

    // Get review by ID
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Review>>
    getReviewById(
            @PathVariable Long reviewId) {

        Review review =
                reviewService.getReviewById(reviewId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Review fetched successfully",
                        review
                )
        );
    }

    // Delete review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<String>>
    deleteReview(
            @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Review deleted successfully",
                        null
                )
        );
    }
}