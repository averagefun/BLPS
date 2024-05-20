package ru.ifmo.main.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.ifmo.main.model.User;
import ru.ifmo.main.repository.UserRepository;
import ru.ifmo.main.security.JwtService;

@Component
public class GenerateTokenDelegate implements JavaDelegate {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public GenerateTokenDelegate(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);
        execution.setVariable("token", token);
    }
}