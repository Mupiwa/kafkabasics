package org.mupiwa.kafka.service;

import org.mupiwa.kafka.common.GdrMessage;
import org.mupiwa.kafka.entity.ProcessedMessage;
import org.mupiwa.kafka.repository.ProcessedMessageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaConsumerService {

    private final ProcessedMessageRepository processedMessageRepository;

    public KafkaConsumerService(ProcessedMessageRepository processedMessageRepository) {
        this.processedMessageRepository = processedMessageRepository;
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(GdrMessage message) {
        System.out.printf("Consumed message: messageId=%d, businessDate=%s, sourceTag=%s, entity=%s%n",
                message.getMessageId(), message.getBusinessDate(), message.getSourceTag(), message.getEntity());

        ProcessedMessage processedMessage = new ProcessedMessage();
        processedMessage.setMessageId(message.getMessageId());
        processedMessage.setEod(message.isEod());
        processedMessage.setBusinessDate(message.getBusinessDate());
        processedMessage.setSourceTag(message.getSourceTag());
        processedMessage.setEntity(message.getEntity());
        processedMessage.setProcessedAt(LocalDateTime.now());

        processedMessageRepository.save(processedMessage);
    }
}
