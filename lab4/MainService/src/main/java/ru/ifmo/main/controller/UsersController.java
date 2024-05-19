package ru.ifmo.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.main.model.User;
import ru.ifmo.main.service.UsersService;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/myself")
    public ResponseEntity<User> mySelf() {
        User user = usersService.getAuthorizedUser();
        return ResponseEntity.ok(user);
    }
}

