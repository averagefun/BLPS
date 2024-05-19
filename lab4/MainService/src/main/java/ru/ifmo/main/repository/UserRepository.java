package ru.ifmo.main.repository;

import java.util.List;
import java.util.Optional;

import ru.ifmo.main.model.User;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    void save(User user);
    List<User> findAll();
}
