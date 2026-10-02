package com.smartfood.backend.controller;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.entity.Order;
import com.smartfood.backend.entity.OrderStatus;
import com.smartfood.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/{userId}")
    public ResponseEntity<ApiResponse<Order>> placeOrder(
            @PathVariable Long userId) {

        Order order = orderService.placeOrder(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order placed successfully",
                        order
                )
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(
            @PathVariable Long orderId) {

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order fetched successfully",
                        order
                )
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Order>>> getUserOrders(
            @PathVariable Long userId) {

        List<Order> orders =
                orderService.getUserOrders(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User orders fetched successfully",
                        orders
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {

        List<Order> orders =
                orderService.getAllOrders();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "All orders fetched successfully",
                        orders
                )
        );
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        OrderStatus orderStatus;

        try {
            orderStatus =
                    OrderStatus.valueOf(
                            status.toUpperCase()
                    );
        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(
                            false,
                            "Invalid order status: " + status,
                            null
                    )
            );
        }

        Order updatedOrder =
                orderService.updateOrderStatus(
                        orderId,
                        orderStatus
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order status updated successfully",
                        updatedOrder
                )
        );
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Order>> cancelOrder(
            @PathVariable Long orderId) {

        Order cancelledOrder =
                orderService.cancelOrder(orderId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order cancelled successfully",
                        cancelledOrder
                )
        );
    }
}