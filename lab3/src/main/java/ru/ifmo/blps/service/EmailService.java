package ru.ifmo.blps.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.User;
import ru.ifmo.blps.repository.UserRepositoryImpl;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private final UserRepositoryImpl userRepository; // Предполагается, что у вас есть репозиторий пользователей

    @Autowired
    public EmailService(JavaMailSender mailSender, UserRepositoryImpl userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public void sendEmailToAllUsers(String subject, List<Listing> listings) {
        List<User> users = userRepository.findAll();
        StringBuilder text = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        listings.forEach(listing -> text.append(listing.toString(userRepository.findById(listing.getAuthorId()).get().getUsername(), formatter)).append("\n"));
        log.info("Sending email to {} users", users.size());
        users.forEach(user -> {
            log.info("Sending email to {}", user.getEmail());
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Pima12390@yandex.ru");
            message.setTo(user.getEmail());
            message.setSubject(subject);
            message.setText(text.append("\n").toString());
            mailSender.send(message);
        });
    }
}