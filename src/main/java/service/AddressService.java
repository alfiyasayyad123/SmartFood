package com.smartfood.backend.service;

import com.smartfood.backend.entity.Address;
import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.AddressRepository;
import com.smartfood.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(
            AddressRepository addressRepository,
            UserRepository userRepository) {

        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Add address
    public Address addAddress(Long userId, Address address) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        address.setUser(user);

        return addressRepository.save(address);
    }

    // Get all addresses of user
    public List<Address> getUserAddresses(Long userId) {

        return addressRepository.findByUserId(userId);
    }

    // Get address by ID
    public Address getAddressById(Long addressId) {

        return addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));
    }

    // Update address
    public Address updateAddress(
            Long addressId,
            Address updatedAddress) {

        Address existingAddress =
                getAddressById(addressId);

        existingAddress.setFullName(
                updatedAddress.getFullName()
        );

        existingAddress.setPhone(
                updatedAddress.getPhone()
        );

        existingAddress.setAddressLine(
                updatedAddress.getAddressLine()
        );

        existingAddress.setCity(
                updatedAddress.getCity()
        );

        existingAddress.setState(
                updatedAddress.getState()
        );

        existingAddress.setPincode(
                updatedAddress.getPincode()
        );

        existingAddress.setLandmark(
                updatedAddress.getLandmark()
        );

        return addressRepository.save(existingAddress);
    }

    // Delete address
    public void deleteAddress(Long addressId) {

        if (!addressRepository.existsById(addressId)) {
            throw new RuntimeException("Address not found");
        }

        addressRepository.deleteById(addressId);
    }
}