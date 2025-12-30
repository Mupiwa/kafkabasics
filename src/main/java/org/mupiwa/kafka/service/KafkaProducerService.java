package org.mupiwa.kafka.service;

import org.mupiwa.kafka.common.GdrMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, GdrMessage> kafkaTemplate;
    private final SourceEntityBusinessDateService sourceEntityBusinessDateService;

    @Value("${kafka.topic}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<String, GdrMessage> kafkaTemplate,
                                SourceEntityBusinessDateService sourceEntityBusinessDateService) {
        this.kafkaTemplate = kafkaTemplate;
        this.sourceEntityBusinessDateService = sourceEntityBusinessDateService;
    }

    public void sendMessage(GdrMessage message) {
        if (!message.isEod()) {
            String businessDate = sourceEntityBusinessDateService.getBusinessDate(
                message.getSourceTag(), message.getEntity());
            message.setBusinessDate(businessDate);
        }
        
        String partitionKey = message.getSourceTag() + "-" + message.getEntity();
        kafkaTemplate.send(topic, partitionKey, message);
        
        System.out.printf("Sent message with key=%s, messageId=%d, businessDate=%s%n",
            partitionKey, message.getMessageId(), message.getBusinessDate());
    }
}
