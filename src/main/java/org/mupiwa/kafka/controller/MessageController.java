package org.mupiwa.kafka.controller;

import org.mupiwa.kafka.common.GdrMessage;
import org.mupiwa.kafka.entity.ProcessedMessage;
import org.mupiwa.kafka.repository.ProcessedMessageRepository;
import org.mupiwa.kafka.service.KafkaProducerService;
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

    public MessageController(KafkaProducerService kafkaProducerService,
                            ProcessedMessageRepository processedMessageRepository) {
        this.kafkaProducerService = kafkaProducerService;
        this.processedMessageRepository = processedMessageRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("gdrMessage", new GdrMessage());
        model.addAttribute("processedMessages", processedMessageRepository.findAllByOrderByProcessedAtDesc());
        return "index";
    }

    @PostMapping("/send")
    public String sendMessage(@ModelAttribute GdrMessage gdrMessage, Model model) {
        kafkaProducerService.sendMessage(gdrMessage);
        model.addAttribute("gdrMessage", new GdrMessage());
        model.addAttribute("processedMessages", processedMessageRepository.findAllByOrderByProcessedAtDesc());
        model.addAttribute("successMessage", "Message sent successfully!");
        return "index";
    }

    @GetMapping("/api/messages")
    @ResponseBody
    public List<ProcessedMessage> getProcessedMessages() {
        return processedMessageRepository.findAllByOrderByProcessedAtDesc();
    }
}
