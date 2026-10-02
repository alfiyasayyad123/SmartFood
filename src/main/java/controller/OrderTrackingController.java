package com.smartfood.backend.controller;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.entity.Order;
import com.smartfood.backend.entity.OrderStatus;
import com.smartfood.backend.service.OrderTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracking")
public class OrderTrackingController {

    private final OrderTrackingService orderTrackingService;

    public OrderTrackingController(
            OrderTrackingService orderTrackingService) {
        this.orderTrackingService = orderTrackingService;
    }

    // Get current order status
    @GetMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderStatus>>
    getOrderStatus(
            @PathVariable Long orderId) {

        OrderStatus status =
                orderTrackingService.getOrderStatus(orderId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order status fetched successfully",
                        status
                )
        );
    }

    // Update order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Order>>
    updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        Order order =
                orderTrackingService.updateStatus(
                        orderId,
                        status
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order status updated successfully",
                        order
                )
        );
    }
}