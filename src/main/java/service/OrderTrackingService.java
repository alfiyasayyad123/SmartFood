package com.smartfood.backend.service;

import com.smartfood.backend.entity.Order;
import com.smartfood.backend.entity.OrderStatus;
import com.smartfood.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderTrackingService {

    private final OrderRepository orderRepository;

    public OrderTrackingService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Get current order status
    public OrderStatus getOrderStatus(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        return order.getStatus();
    }

    // Update order status
    public Order updateStatus(
            Long orderId,
            OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setStatus(status);

        return orderRepository.save(order);
    }
}