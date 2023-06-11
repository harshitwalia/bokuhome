package com.boku.backend.api.dtos;

import com.boku.backend.external.Address;
import com.boku.backend.external.WithdrawalId;

import java.util.UUID;

public class WithdrawalRequestDto {
    private UUID senderId;
    private Address address;
    private double amount;

    public WithdrawalRequestDto(UUID senderId, Address address, double amount) {
        this.senderId = senderId;
        this.address = address;
        this.amount = amount;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
