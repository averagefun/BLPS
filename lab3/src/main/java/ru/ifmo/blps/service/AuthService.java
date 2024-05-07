package ru.ifmo.blps.service;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.UUID;

import org.openapitools.model.AuthRequest;
import org.openapitools.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.blps.exceptions.UserAlreadyExistsException;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.model.enums.Role;
import ru.ifmo.blps.repository.UserRepository;
import ru.ifmo.blps.security.JwtService;

@Service
public class AuthService {
    private final UsersService usersService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UsersService usersService, UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.usersService = usersService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(AuthRequest request) {
        Optional<User> userO = userRepository.findByUsername(request.getEmail());
        if (userO.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        if (validateUsername(request.getEmail())) {
            throw new InvalidParameterException("Invalid username");
        }

        User user = User.builder()
                .id(Math.abs(UUID.randomUUID().getLeastSignificantBits()))
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    private boolean validateUsername(String username) {
        return username.isBlank();
    }
}
