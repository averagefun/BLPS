package ru.ifmo.verification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ifmo.verification.dto.ListingVerificationMessage;

@Slf4j
@Service
public class KafkaConsumerService {
    private final TelegramBotService telegramBotService;

    public KafkaConsumerService(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @RetryableTopic
    @KafkaListener(topics = "ListingsVerification", groupId = "ListingsVerificationGroup")
    public void consumeMessage(ListingVerificationMessage message) {
        try {
            telegramBotService.sendMessage(message.text());
        } catch (TelegramApiException e) {
            log.error("Telegram API error", e);
            throw new RuntimeException(e);
        }
    }
}
