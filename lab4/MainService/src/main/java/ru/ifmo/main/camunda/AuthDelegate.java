package ru.ifmo.main.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.openapitools.model.AuthRequest;
import org.openapitools.model.AuthResponse;
import org.springframework.stereotype.Component;
import ru.ifmo.main.service.AuthService;

@Component("authDelegate")
public class AuthDelegate implements JavaDelegate {

    private final AuthService authService;

    public AuthDelegate(AuthService authService) {
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
            AuthResponse authResponse = authService.authenticate(authRequest);
            execution.setVariable("isAuthenticated", true);
            execution.setVariable("token", authResponse.getToken());
        } catch (Exception e) {
            execution.setVariable("isAuthenticated", false);
            execution.setVariable("errorMessage", e.getMessage());
        }
    }
}