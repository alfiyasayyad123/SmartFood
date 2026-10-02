package com.smartfood.backend.service;

import com.smartfood.backend.entity.Cart;
import com.smartfood.backend.entity.Order;
import com.smartfood.backend.entity.OrderItem;
import com.smartfood.backend.entity.OrderStatus;
import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.CartRepository;
import com.smartfood.backend.repository.OrderItemRepository;
import com.smartfood.backend.repository.OrderRepository;
import com.smartfood.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    // ==========================================
    // PLACE ORDER FROM USER CART
    // ==========================================
    @Transactional
    public Order placeOrder(Long userId) {

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + userId)
                );

        // Get user's cart
        List<Cart> cartItems = cartRepository.findByUserId(userId);

        // Check cart
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Create new order
        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setTotalAmount(0.0);

        // Save order first
        Order savedOrder = orderRepository.save(order);

        double totalAmount = 0.0;

        // Convert Cart items to OrderItems
        for (Cart cart : cartItems) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setFood(cart.getFood());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setPrice(cart.getFood().getPrice());

            // Save OrderItem
            OrderItem savedOrderItem =
                    orderItemRepository.save(orderItem);

            // Add item to order
            savedOrder.getItems().add(savedOrderItem);

            // Calculate total
            totalAmount +=
                    cart.getFood().getPrice()
                            * cart.getQuantity();
        }

        // Set final total
        savedOrder.setTotalAmount(totalAmount);

        // Save updated order
        savedOrder = orderRepository.save(savedOrder);

        // Clear cart after successful order
        cartRepository.deleteByUserId(userId);

        return savedOrder;
    }

    // ==========================================
    // CREATE ORDER
    // ==========================================
    @Transactional
    public Order createOrder(Order order) {

        order.setOrderDate(LocalDateTime.now());

        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PLACED);
        }

        double totalAmount = 0.0;

        Order savedOrder = orderRepository.save(order);

        if (order.getItems() != null) {

            for (OrderItem item : order.getItems()) {

                item.setOrder(savedOrder);

                if (item.getFood() != null) {
                    totalAmount +=
                            item.getPrice() * item.getQuantity();
                }

                orderItemRepository.save(item);
            }
        }

        savedOrder.setTotalAmount(totalAmount);

        return orderRepository.save(savedOrder);
    }

    // ==========================================
    // GET ORDER BY ID
    // ==========================================
    public Order getOrderById(Long orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with ID: " + orderId
                        )
                );
    }

    // ==========================================
    // GET USER'S ORDERS
    // ==========================================
    public List<Order> getUserOrders(Long userId) {

        return orderRepository.findByUserId(userId);
    }

    // ==========================================
    // UPDATE ORDER STATUS
    // ==========================================
    public Order updateOrderStatus(
            Long orderId,
            OrderStatus status
    ) {

        Order order = getOrderById(orderId);

        order.setStatus(status);

        return orderRepository.save(order);
    }

    // ==========================================
    // CANCEL ORDER
    // ==========================================
    public Order cancelOrder(Long orderId) {

        Order order = getOrderById(orderId);

        order.setStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }

    // ==========================================
    // GET ALL ORDERS
    // ==========================================
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }
}