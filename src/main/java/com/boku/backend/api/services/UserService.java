package com.boku.backend.api.services;

import com.boku.backend.api.dtos.UserDto;
import com.boku.backend.api.exceptions.UserNotFoundException;
import com.boku.backend.api.models.User;
import com.boku.backend.api.repositories.UserRepository;
import com.boku.backend.api.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return user.get();
    }

    public UserDto getUserDtoById(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return new UserDto(user.get().getId(),user.get().getName(),user.get().getBalance());
    }

    public User createUser(String name,double initialBalance) {
        User user = new User(UUID.randomUUID(), name, initialBalance);
        userRepository.save(user);
        return user;
    }

    public UserDto createUserFromUserDto(UserDto userDto) {
        User user = new User(userDto.getId(), userDto.getName(), userDto.getBalance());
        userRepository.save(user);
        return new UserDto(user.getId(),user.getName(),user.getBalance());
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

    public UserDto updateUser(UUID userId, UserDto userDto) {
        User user = getUserById(userId);
        user.setName(userDto.getName());
        user.setBalance(userDto.getBalance());
        return new UserDto(user.getId(),user.getName(),user.getBalance());
    }
    public boolean deleteUser(UUID userId) {
        userRepository.deleteUser(userId);
        return true;
    }
}
