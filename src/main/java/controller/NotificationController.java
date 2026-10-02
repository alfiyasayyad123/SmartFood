package com.smartfood.backend.controller;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.entity.Notification;
import com.smartfood.backend.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Create notification
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Notification>>
    createNotification(
            @RequestParam Long userId,
            @RequestParam String message) {

        Notification notification =
                notificationService.createNotification(
                        userId,
                        message
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Notification created successfully",
                        notification
                )
        );
    }

    // Get all notifications
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Notification>>>
    getUserNotifications(
            @PathVariable Long userId) {

        List<Notification> notifications =
                notificationService.getUserNotifications(
                        userId
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Notifications fetched successfully",
                        notifications
                )
        );
    }

    // Get unread notifications
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<ApiResponse<List<Notification>>>
    getUnreadNotifications(
            @PathVariable Long userId) {

        List<Notification> notifications =
                notificationService.getUnreadNotifications(
                        userId
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Unread notifications fetched successfully",
                        notifications
                )
        );
    }

    // Mark notification as read
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Notification>>
    markAsRead(
            @PathVariable Long notificationId) {

        Notification notification =
                notificationService.markAsRead(
                        notificationId
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Notification marked as read",
                        notification
                )
        );
    }

    // Delete notification
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<String>>
    deleteNotification(
            @PathVariable Long notificationId) {

        notificationService.deleteNotification(
                notificationId
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Notification deleted successfully",
                        null
                )
        );
    }
}