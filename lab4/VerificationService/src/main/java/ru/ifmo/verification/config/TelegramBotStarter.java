package ru.ifmo.verification.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ifmo.verification.service.TelegramBotService;

@Slf4j
@Component
public class TelegramBotStarter {
    private final TelegramBotService bot;

    public TelegramBotStarter(TelegramBotService bot) {
        this.bot = bot;
    }

    @EventListener({ApplicationReadyEvent.class})
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            log.info("Telegram bot started");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
