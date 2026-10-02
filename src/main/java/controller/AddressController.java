package com.smartfood.backend.controller;

import com.smartfood.backend.dto.ApiResponse;
import com.smartfood.backend.entity.Address;
import com.smartfood.backend.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Add address
    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Address>> addAddress(
            @PathVariable Long userId,
            @RequestBody Address address) {

        Address savedAddress =
                addressService.addAddress(userId, address);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Address added successfully",
                        savedAddress
                )
        );
    }

    // Get user's addresses
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Address>>>
    getUserAddresses(
            @PathVariable Long userId) {

        List<Address> addresses =
                addressService.getUserAddresses(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Addresses fetched successfully",
                        addresses
                )
        );
    }

    // Get address by ID
    @GetMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Address>>
    getAddressById(
            @PathVariable Long addressId) {

        Address address =
                addressService.getAddressById(addressId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Address fetched successfully",
                        address
                )
        );
    }

    // Update address
    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Address>>
    updateAddress(
            @PathVariable Long addressId,
            @RequestBody Address address) {

        Address updatedAddress =
                addressService.updateAddress(
                        addressId,
                        address
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Address updated successfully",
                        updatedAddress
                )
        );
    }

    // Delete address
    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<String>>
    deleteAddress(
            @PathVariable Long addressId) {

        addressService.deleteAddress(addressId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Address deleted successfully",
                        null
                )
        );
    }
}