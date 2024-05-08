package ru.ifmo.verification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.ifmo.verification.dto.ListingVerificationMessage;

@Slf4j
@Service
public class KafkaConsumerService {
    private final TelegramBotService telegramBotService;

    public KafkaConsumerService(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @KafkaListener(topics = "ListingsVerification", groupId = "ListingsVerificationGroup")
    public void consumeMessage(ListingVerificationMessage message) {
        telegramBotService.sendMessage(message.text());
    }
}
