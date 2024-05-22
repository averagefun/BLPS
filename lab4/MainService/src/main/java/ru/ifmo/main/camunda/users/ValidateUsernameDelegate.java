package ru.ifmo.main.camunda.users;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ValidateUsernameDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");
        boolean usernameValid = username != null && !username.trim().isEmpty();
        execution.setVariable("usernameValid", usernameValid);
    }
}