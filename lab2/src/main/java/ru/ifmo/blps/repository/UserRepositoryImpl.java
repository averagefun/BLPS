package ru.ifmo.blps.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.service.UsersXmlService;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UsersXmlService usersXmlService;

    @Autowired
    public UserRepositoryImpl(UsersXmlService usersXmlService) {
        this.usersXmlService = usersXmlService;
    }

    @Override
    public Optional<User> findById(Long id) {
        return usersXmlService.readUsersFromXml().stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return usersXmlService.readUsersFromXml().stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    @Override
    public void save(User user) {
        List<User> users = usersXmlService.readUsersFromXml();
        users.remove(user);
        users.add(user);
        usersXmlService.writeUsersToXml(users);
    }
}
