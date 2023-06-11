package com.boku.backend.api.services;

import com.boku.backend.api.dtos.UserDto;
import com.boku.backend.api.exceptions.UserNotFoundException;
import com.boku.backend.api.models.User;
import com.boku.backend.api.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void testGetUser_Success() throws UserNotFoundException {
        // Prepare test data
        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, "John Doe",100.0);
        UserDto expectedUserDto = new UserDto(uuid, "John Doe",100.0);

        // Mock the repository behavior
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        // Perform the operation
        User result = userService.getUserById(uuid);
        UserDto resultUserDto = new UserDto(result.getId(),result.getName(),result.getBalance());
        // Verify the result
        Assertions.assertEquals(expectedUserDto, resultUserDto);
    }

    @Test
    public void testGetUser_UserNotFound() {
        // Perform the operation and verify the exception
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(UUID.randomUUID()));
    }

    // Add more test cases for other UserService methods

}
