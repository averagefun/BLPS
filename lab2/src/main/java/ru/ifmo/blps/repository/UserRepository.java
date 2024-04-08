package ru.ifmo.blps.repository;

import java.util.Optional;

import ru.ifmo.blps.model.User;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    void save(User user);
}
