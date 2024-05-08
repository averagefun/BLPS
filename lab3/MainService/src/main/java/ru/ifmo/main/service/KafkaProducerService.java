package ru.ifmo.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> template;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void sendMessage(String topicName, String message) {
        template.send(topicName, message);
    }
}
