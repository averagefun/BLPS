package ru.ifmo.verification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("bot")
public class BotConfiguration {
    private String token;
    private String adminId;
    private String messagePrefix;
}
