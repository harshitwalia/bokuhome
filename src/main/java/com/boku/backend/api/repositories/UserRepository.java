package com.boku.backend.api.repositories;

import com.boku.backend.api.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public void save(User user) {
        users.add(user);
    }

    public Optional<User> findById(UUID userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();
    }

    public List<User> getUsers() {
        return users;
    }
    public void deleteUser(UUID userId) {
        Optional<User> user = findById(userId);
        user.ifPresent(users::remove);
    }
}
