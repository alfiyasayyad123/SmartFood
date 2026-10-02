package com.smartfood.backend.service;

import com.smartfood.backend.entity.Notification;
import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.NotificationRepository;
import com.smartfood.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository) {

        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // Create notification
    public Notification createNotification(
            Long userId,
            String message) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setMessage(message);
        notification.setReadStatus(false);
        notification.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    // Get all notifications
    public List<Notification> getUserNotifications(
            Long userId) {

        return notificationRepository
                .findByUserId(userId);
    }

    // Get unread notifications
    public List<Notification> getUnreadNotifications(
            Long userId) {

        return notificationRepository
                .findByUserIdAndReadStatus(
                        userId,
                        false
                );
    }

    // Mark notification as read
    public Notification markAsRead(Long notificationId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        notification.setReadStatus(true);

        return notificationRepository.save(notification);
    }

    // Delete notification
    public void deleteNotification(
            Long notificationId) {

        if (!notificationRepository
                .existsById(notificationId)) {

            throw new RuntimeException(
                    "Notification not found"
            );
        }

        notificationRepository.deleteById(
                notificationId
        );
    }
}