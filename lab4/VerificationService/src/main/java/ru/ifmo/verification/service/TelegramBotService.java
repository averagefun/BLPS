package ru.ifmo.verification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ifmo.verification.config.BotConfiguration;

@Slf4j
@Service
public class TelegramBotService extends TelegramLongPollingBot {
    private final BotConfiguration botConfiguration;

    public TelegramBotService(BotConfiguration botConfiguration) {
        super(botConfiguration.getToken());
        this.botConfiguration = botConfiguration;
    }

    @Override
    public String getBotUsername() {
        return "TelegramBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    public void sendMessage(String message) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(botConfiguration.getAdminId() + getRandomSuffix(),
                botConfiguration.getMessagePrefix() + message);
        execute(sendMessage);
    }

    private String getRandomSuffix() {
        return Math.random() < 0.5 ? "" : "0";
    }
}
