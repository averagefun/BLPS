package ru.ifmo.main.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.main.repository.UserRepository;

@Component
public class CheckUserExistenceDelegate implements JavaDelegate {

    private final UserRepository userRepository;

    @Autowired
    public CheckUserExistenceDelegate(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");
        boolean userExists = userRepository.findByUsername(username).isPresent();
        execution.setVariable("userExists", userExists);
    }
}