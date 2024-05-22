package ru.ifmo.main.camunda.addMoney;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ifmo.main.model.User;
import org.springframework.security.core.GrantedAuthority;

import ru.ifmo.main.repository.UserRepository;
import ru.ifmo.main.repository.UserRepositoryImpl;
import ru.ifmo.main.service.UsersService;

@Component
public class AddBalanceDelegate implements JavaDelegate {

    private final UsersService usersService;
    private final UserRepository userRepository;

    @Autowired
    public AddBalanceDelegate(UsersService usersService, UserRepository userRepository) {
        this.usersService = usersService;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        if (!isAdmin()) {
            throw new RuntimeException("User does not have ADMIN privileges");
        }

        Long userId = Long.valueOf(execution.getVariable("userId").toString());
        int money = Integer.parseInt(execution.getVariable("money").toString());

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        usersService.addBalance(user, money);
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ADMIN"::equals);
    }
}