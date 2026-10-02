package com.smartfood.backend.service;

import com.smartfood.backend.entity.Restaurant;
import com.smartfood.backend.entity.Review;
import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.RestaurantRepository;
import com.smartfood.backend.repository.ReviewRepository;
import com.smartfood.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            RestaurantRepository restaurantRepository) {

        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // Add review
    public Review addReview(
            Long userId,
            Long restaurantId,
            int rating,
            String comment) {

        if (rating < 1 || rating > 5) {
            throw new RuntimeException(
                    "Rating must be between 1 and 5"
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Restaurant restaurant =
                restaurantRepository.findById(restaurantId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Restaurant not found"));

        Review review = new Review();

        review.setUser(user);
        review.setRestaurant(restaurant);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    // Get restaurant reviews
    public List<Review> getRestaurantReviews(
            Long restaurantId) {

        return reviewRepository
                .findByRestaurantId(restaurantId);
    }

    // Get user's reviews
    public List<Review> getUserReviews(Long userId) {

        return reviewRepository.findByUserId(userId);
    }

    // Get review by ID
    public Review getReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Review not found"));
    }

    // Delete review
    public void deleteReview(Long reviewId) {

        if (!reviewRepository.existsById(reviewId)) {
            throw new RuntimeException(
                    "Review not found");
        }

        reviewRepository.deleteById(reviewId);
    }
}