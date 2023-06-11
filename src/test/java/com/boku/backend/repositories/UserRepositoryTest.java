package com.boku.backend.repositories;

import com.boku.backend.api.repositories.UserRepository;
import com.boku.backend.api.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;


public class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        // Initialize UserRepository or mock it using a mocking framework
        userRepository = new UserRepository();
    }

    @Test
    public void testSaveUser() {
        // Create a new user
        User user = new User(UUID.randomUUID(),"John Doe", 100.0);

        // Save the user
        userRepository.save(user);

        // Retrieve the saved user
        User savedUser = userRepository.findById(user.getId()).get();

        // Check if the saved user is not null and has the same properties
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(user.getId(), savedUser.getId());
        Assertions.assertEquals(user.getName(), savedUser.getName());
        Assertions.assertEquals(user.getBalance(), savedUser.getBalance());
    }

    @Test
    public void testGetUserById() {
        // Create a new user
        User user = new User(UUID.randomUUID(),"John Doe", 100.0);

        // Save the user
        userRepository.save(user);

        // Retrieve the user by ID
        User retrievedUser = userRepository.findById(user.getId()).get();

        // Check if the retrieved user is not null and has the same properties
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals(user.getId(), retrievedUser.getId());
        Assertions.assertEquals(user.getName(), retrievedUser.getName());
        Assertions.assertEquals(user.getBalance(), retrievedUser.getBalance());
    }

    // Add more test cases for other UserRepository methods

}
