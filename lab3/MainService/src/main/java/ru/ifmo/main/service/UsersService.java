package ru.ifmo.main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ifmo.main.exceptions.NotEnoughBalanceException;
import ru.ifmo.main.model.User;
import ru.ifmo.main.repository.UserRepository;

@Service
public class UsersService {
    private final UserRepository userRepository;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getAuthorizedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void addBalance(User user, int money) {
        user.setBalance(user.getBalance() + money);
        userRepository.save(user);
    }

    public void pay(User user, int cost) {
        if (user.getBalance() < cost) {
            throw new NotEnoughBalanceException();
        }

        user.setBalance(user.getBalance() - cost);
        userRepository.save(user);
    }
}
