package ru.ifmo.blps.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.RentListing;
import ru.ifmo.blps.model.User;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    void save(User user);
    List<User> findAll();
}
