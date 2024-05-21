package ru.ifmo.main.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ifmo.main.repository.UserRepository;
import ru.ifmo.main.security.JwtService;

@Component
public class AuthenticateUserDelegate implements JavaDelegate {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticateUserDelegate(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String token = (String) execution.getVariable("token");

        if (token == null || !jwtService.validateToken(token)) {
            throw new RuntimeException("Invalid JWT Token" + token);
        }

        Authentication authentication = jwtService.getAuthentication(token);
        execution.setVariable("authorizedUser", userRepository.findByUsername(authentication.getName()).get());
        execution.setVariable("authenticatedUsername", authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
