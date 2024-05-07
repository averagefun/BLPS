package ru.ifmo.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> template;

    public void sendMessage(String topicName, String message) {
        template.send(topicName, message);
    }
}
