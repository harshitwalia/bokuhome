package com.boku.backend.api.dtos;

import java.util.Objects;
import java.util.UUID;

public class UserDto {
    private UUID id;
    private String name;

    private double balance;


    public UserDto(UUID id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
// Optionally, you can override the equals() and hashCode() methods for proper object comparison.

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserDto userDto = (UserDto) obj;
        return id.equals(userDto.id) && name.equals(userDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
