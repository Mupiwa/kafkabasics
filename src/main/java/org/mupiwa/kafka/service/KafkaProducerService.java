package org.mupiwa.kafka.service;

import org.mupiwa.kafka.common.GdrMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, GdrMessage> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<String, GdrMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(GdrMessage message) {
        kafkaTemplate.send(topic, String.valueOf(message.getMessageId()), message);
    }
}
