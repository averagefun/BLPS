package ru.ifmo.blps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.blps.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
