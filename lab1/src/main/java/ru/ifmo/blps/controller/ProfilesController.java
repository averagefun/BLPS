package ru.ifmo.blps.controller;

import org.openapitools.model.CreateProfileRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfilesController {
    @GetMapping("/account/deposit")
    public String controller(CreateProfileRequest request){
        System.out.println("Amogus");
        return "";
    }
}
