package ru.ifmo.main;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableProcessApplication
public class MainServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainServiceApplication.class, args);
    }
}
