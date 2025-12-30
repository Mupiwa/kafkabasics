package org.mupiwa.kafka.controller;

import org.mupiwa.kafka.common.GdrMessage;
import org.mupiwa.kafka.entity.ProcessedMessage;
import org.mupiwa.kafka.entity.SourceEntityBusinessDateEntity;
import org.mupiwa.kafka.repository.ProcessedMessageRepository;
import org.mupiwa.kafka.service.KafkaProducerService;
import org.mupiwa.kafka.service.SourceEntityBusinessDateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MessageController {

    private final KafkaProducerService kafkaProducerService;
    private final ProcessedMessageRepository processedMessageRepository;
    private final SourceEntityBusinessDateService sourceEntityBusinessDateService;

    public MessageController(KafkaProducerService kafkaProducerService,
                            ProcessedMessageRepository processedMessageRepository,
                            SourceEntityBusinessDateService sourceEntityBusinessDateService) {
        this.kafkaProducerService = kafkaProducerService;
        this.processedMessageRepository = processedMessageRepository;
        this.sourceEntityBusinessDateService = sourceEntityBusinessDateService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("gdrMessage", new GdrMessage());
        model.addAttribute("processedMessages", processedMessageRepository.findAllByOrderByProcessedAtDesc());
        model.addAttribute("businessDates", sourceEntityBusinessDateService.getAllBusinessDates());
        return "index";
    }

    @PostMapping("/send")
    public String sendMessage(@ModelAttribute GdrMessage gdrMessage, Model model) {
        kafkaProducerService.sendMessage(gdrMessage);
        model.addAttribute("gdrMessage", new GdrMessage());
        model.addAttribute("processedMessages", processedMessageRepository.findAllByOrderByProcessedAtDesc());
        model.addAttribute("businessDates", sourceEntityBusinessDateService.getAllBusinessDates());
        model.addAttribute("successMessage", "Message sent successfully!");
        return "index";
    }

    @GetMapping("/api/messages")
    @ResponseBody
    public List<ProcessedMessage> getProcessedMessages() {
        return processedMessageRepository.findAllByOrderByProcessedAtDesc();
    }

    @GetMapping("/api/business-dates")
    @ResponseBody
    public List<SourceEntityBusinessDateEntity> getBusinessDates() {
        return sourceEntityBusinessDateService.getAllBusinessDates();
    }
}
