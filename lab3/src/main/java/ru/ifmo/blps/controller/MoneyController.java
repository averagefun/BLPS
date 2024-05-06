package ru.ifmo.blps.controller;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.AddMoneyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.service.UsersService;

@Slf4j
@RestController
@RequestMapping("/balance")
public class MoneyController {
    private final UsersService usersService;

    @Autowired
    public MoneyController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<Integer> getBalance() {
        return ResponseEntity.ok(usersService.getAuthorizedUser().getBalance());
    }

    @Secured("ADMIN")
    @PostMapping("/add")
    public void addBalance(@RequestBody AddMoneyRequest request) {
        User user = usersService.getAuthorizedUser();
        usersService.addBalance(user, request.getMoney());
    }
}
