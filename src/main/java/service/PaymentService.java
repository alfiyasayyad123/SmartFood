package com.smartfood.backend.service;

import com.smartfood.backend.entity.Order;
import com.smartfood.backend.entity.Payment;
import com.smartfood.backend.entity.PaymentMethod;
import com.smartfood.backend.entity.PaymentStatus;
import com.smartfood.backend.repository.OrderRepository;
import com.smartfood.backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // Make payment for an order
    public Payment makePayment(
            Long orderId,
            PaymentMethod method) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        Payment existingPayment =
                paymentRepository.findByOrderId(orderId)
                        .orElse(null);

        if (existingPayment != null) {
            throw new RuntimeException(
                    "Payment already exists for this order"
            );
        }

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // Get payment by order
    public Payment getPaymentByOrderId(Long orderId) {

        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found"
                        ));
    }

    // Get payment by payment ID
    public Payment getPaymentById(Long paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found"
                        ));
    }

    // Refund payment
    public Payment refundPayment(Long paymentId) {

        Payment payment = getPaymentById(paymentId);

        payment.setStatus(PaymentStatus.REFUNDED);

        return paymentRepository.save(payment);
    }
}