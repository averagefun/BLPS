package ru.ifmo.blps.controller;

import org.openapitools.model   .AuthRequest;
import org.openapitools.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.blps.exceptions.UserAlreadyExistsException;
import ru.ifmo.blps.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(ErrorResponse.builder()
                    .description("User with username '" + request.getUsername() + "' not found").build());
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder()
                    .description("Wrong password").build());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body(ErrorResponse.builder()
                    .description("User with username '" + request.getUsername() + "' already exists").build());
        }
    }
}