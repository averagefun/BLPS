package ru.ifmo.blps.worker;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.blps.model.Listing;
import ru.ifmo.blps.model.enums.ListingStatus;
import ru.ifmo.blps.repository.RentListingsRepository;
import ru.ifmo.blps.repository.SaleListingsRepository;
import ru.ifmo.blps.service.EmailService;
import ru.ifmo.blps.service.ListingsService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class ListingNotificationJob implements Job {

    private final ListingsService listingsService;

    private final EmailService emailService; // Предполагается, что у вас есть сервис для отправки email

    @Autowired
    public ListingNotificationJob(ListingsService listingsService, EmailService emailService) {
        this.listingsService = listingsService;
        this.emailService = emailService;
    }


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Listing> listings = listingsService.findByStatusAndCreatedTime(ListingStatus.LISTED);
        log.info("sending {} mails", listings.size());
        emailService.sendEmailToAllUsers("New Listing Available", listings); // Пример метода

    }
}