package ru.ifmo.blps.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.blps.service.UsersService;

@Slf4j
@RestController
@RequestMapping("/balance")
public class MoneyController {

    @Autowired
    public MoneyController(UsersService usersService) {
    }

    @PostMapping("/add")
    public void addMoney(@RequestBody int money) {
        // todo
        log.info("Добавлено " + money + " рубасиков");
    }

    @GetMapping()
    public ResponseEntity<Integer> getMoney() {
        // todo
//        log.info("На балансе " + balance + " рубасиков");
        return ResponseEntity.ok(0);
    }

}
