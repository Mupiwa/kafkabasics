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
    private final SourceEntityBusinessDateService sourceEntityBusinessDateService;

    public KafkaConsumerService(ProcessedMessageRepository processedMessageRepository,
                                SourceEntityBusinessDateService sourceEntityBusinessDateService) {
        this.processedMessageRepository = processedMessageRepository;
        this.sourceEntityBusinessDateService = sourceEntityBusinessDateService;
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(GdrMessage message) {
        System.out.printf("Consumed message: messageId=%d, isEod=%b, businessDate=%s, sourceTag=%s, entity=%s%n",
                message.getMessageId(), message.isEod(), message.getBusinessDate(), 
                message.getSourceTag(), message.getEntity());

        if (message.isEod()) {
            sourceEntityBusinessDateService.rollBusinessDate(message.getSourceTag(), message.getEntity());
            System.out.printf("EOD message processed: rolled business date for %s-%s%n",
                    message.getSourceTag(), message.getEntity());
            return;
        }

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
