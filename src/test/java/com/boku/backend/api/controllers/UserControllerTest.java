package com.boku.backend.api.controllers;

import com.boku.backend.api.dtos.UserDto;
import com.boku.backend.api.models.User;
import com.boku.backend.api.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.mockito.Mockito.*;

class UserControllerTest {
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void testGetUser() {
        User user = new User(UUID.randomUUID(), "John Doe",100.0);
        UserDto userDto = new UserDto(UUID.randomUUID(), "John Doe",100.0);

        //Set userDto properties
        when(userService.getUserDtoById(user.getId())).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUser(user.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(userDto, response.getBody());

        verify(userService, times(1)).getUserDtoById(user.getId());
    }

    @Test
    void testGetUserNotFound() {
        UUID uuid = UUID.randomUUID();

        ResponseEntity<UserDto> response = userController.getUser(uuid);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        verify(userService, times(1)).getUserDtoById(uuid);
    }
}
