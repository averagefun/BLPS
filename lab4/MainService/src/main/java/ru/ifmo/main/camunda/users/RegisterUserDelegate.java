package ru.ifmo.main.camunda.users;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.openapitools.model.AuthRequest;
import org.openapitools.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.main.exceptions.UserAlreadyExistsException;
import ru.ifmo.main.service.AuthService;

import java.security.InvalidParameterException;

@Component
public class RegisterUserDelegate implements JavaDelegate {

    private final AuthService authService;

    @Autowired
    public RegisterUserDelegate(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");
        String password = (String) execution.getVariable("password");

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(username);
        authRequest.setPassword(password);

        try {
            AuthResponse authResponse = authService.register(authRequest);
            execution.setVariable("token", authResponse.getToken());
            execution.setVariable("isRegistered", true);
        } catch (UserAlreadyExistsException e) {
            execution.setVariable("isRegistered", false);
            execution.setVariable("errorMessage", "User already exists");
        } catch (InvalidParameterException e) {
            execution.setVariable("isRegistered", false);
            execution.setVariable("errorMessage", "Invalid username");
        }
    }
}