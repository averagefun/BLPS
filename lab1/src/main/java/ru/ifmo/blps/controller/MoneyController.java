package ru.ifmo.blps.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.blps.service.UserService;

@Slf4j
@RestController
@RequestMapping("/balance")
public class MoneyController {

    private final UserService userService;

    @Autowired
    public MoneyController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public void addMoney(@RequestBody Integer money) {
        userService.addToBalance(money);
        log.info("Добавлено " + money + " рубасиков");
    }

    @GetMapping("/")
    public ResponseEntity<Integer> getMoney() {
        Integer balance = userService.getBalance();
        log.info("На баланск " + balance + " рубасиков");
        return ResponseEntity.ok(balance);
    }

}
