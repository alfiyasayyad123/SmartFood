package com.smartfood.backend.controller;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.entity.Payment;
import com.smartfood.backend.entity.PaymentMethod;
import com.smartfood.backend.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Make payment
    @PostMapping("/pay/{orderId}")
    public ResponseEntity<ApiResponse<Payment>> makePayment(
            @PathVariable Long orderId,
            @RequestParam PaymentMethod method) {

        Payment payment =
                paymentService.makePayment(
                        orderId,
                        method
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment successful",
                        payment
                )
        );
    }

    // Get payment by order ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<Payment>>
    getPaymentByOrderId(
            @PathVariable Long orderId) {

        Payment payment =
                paymentService.getPaymentByOrderId(
                        orderId
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment fetched successfully",
                        payment
                )
        );
    }

    // Get payment by payment ID
    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<Payment>>
    getPaymentById(
            @PathVariable Long paymentId) {

        Payment payment =
                paymentService.getPaymentById(
                        paymentId
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment fetched successfully",
                        payment
                )
        );
    }

    // Refund payment
    @PutMapping("/{paymentId}/refund")
    public ResponseEntity<ApiResponse<Payment>>
    refundPayment(
            @PathVariable Long paymentId) {

        Payment payment =
                paymentService.refundPayment(
                        paymentId
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment refunded successfully",
                        payment
                )
        );
    }
}