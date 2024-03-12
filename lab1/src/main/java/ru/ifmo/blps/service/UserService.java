package ru.ifmo.blps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.repository.UserRepository;

@Service
public class UserService {
    private static final User user = new User(1L, 100);

    @Autowired
    public UserService(UserRepository userRepository) {
    }

    public User getUser() {
        return user;
    }

    public boolean checkBalance(Integer cost) {
        return user.getBalance() >= cost;
    }

    public void addToBalance(Integer money) {
        user.setBalance(user.getBalance() + money);
    }

    public int getBalance() {
        return user.getBalance();
    }

    public int payFromBalance(Integer money) {
        user.setBalance(user.getBalance() - money);
        return user.getBalance();
    }
}
