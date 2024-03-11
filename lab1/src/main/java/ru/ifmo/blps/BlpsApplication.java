package ru.ifmo.blps;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ifmo.blps.service.UserService;

@SpringBootApplication
public class BlpsApplication {


    public static void main(String[] args) {
        SpringApplication.run(BlpsApplication.class, args);
    }


}
