package org.mupiwa.kafka.service;

import org.mupiwa.kafka.entity.SourceEntityBusinessDateEntity;
import org.mupiwa.kafka.repository.SourceEntityBusinessDateRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class SourceEntityBusinessDateService {

    private final SourceEntityBusinessDateRepository repository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SourceEntityBusinessDateService(SourceEntityBusinessDateRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initializeBusinessDates() {
        String currentDate = LocalDate.now().format(DATE_FORMATTER);
        
        try {
            ClassPathResource resource = new ClassPathResource("source-entity.txt");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String sourceTag = parts[0].trim();
                        String entity = parts[1].trim();
                        
                        Optional<SourceEntityBusinessDateEntity> existing = 
                            repository.findBySourceTagAndEntity(sourceTag, entity);
                        
                        if (existing.isEmpty()) {
                            SourceEntityBusinessDateEntity newEntry = 
                                new SourceEntityBusinessDateEntity(sourceTag, entity, currentDate);
                            repository.save(newEntry);
                            System.out.printf("Initialized business date for %s-%s to %s%n", 
                                sourceTag, entity, currentDate);
                        } else {
                            SourceEntityBusinessDateEntity entry = existing.get();
                            entry.setBusinessDate(currentDate);
                            repository.save(entry);
                            System.out.printf("Reset business date for %s-%s to %s%n", 
                                sourceTag, entity, currentDate);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading source-entity.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getBusinessDate(String sourceTag, String entity) {
        return repository.findBySourceTagAndEntity(sourceTag, entity)
            .map(SourceEntityBusinessDateEntity::getBusinessDate)
            .orElse(LocalDate.now().format(DATE_FORMATTER));
    }

    public void rollBusinessDate(String sourceTag, String entity) {
        Optional<SourceEntityBusinessDateEntity> existing = 
            repository.findBySourceTagAndEntity(sourceTag, entity);
        
        if (existing.isPresent()) {
            SourceEntityBusinessDateEntity entry = existing.get();
            LocalDate currentDate = LocalDate.parse(entry.getBusinessDate(), DATE_FORMATTER);
            LocalDate nextBusinessDate = getNextBusinessDate(currentDate);
            entry.setBusinessDate(nextBusinessDate.format(DATE_FORMATTER));
            repository.save(entry);
            System.out.printf("Rolled business date for %s-%s from %s to %s%n", 
                sourceTag, entity, currentDate, nextBusinessDate);
        } else {
            LocalDate today = LocalDate.now();
            LocalDate nextBusinessDate = getNextBusinessDate(today);
            SourceEntityBusinessDateEntity newEntry = 
                new SourceEntityBusinessDateEntity(sourceTag, entity, nextBusinessDate.format(DATE_FORMATTER));
            repository.save(newEntry);
            System.out.printf("Created and rolled business date for %s-%s to %s%n", 
                sourceTag, entity, nextBusinessDate);
        }
    }

    private LocalDate getNextBusinessDate(LocalDate date) {
        LocalDate nextDate = date.plusDays(1);
        while (nextDate.getDayOfWeek() == DayOfWeek.SATURDAY || 
               nextDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            nextDate = nextDate.plusDays(1);
        }
        return nextDate;
    }

    public List<SourceEntityBusinessDateEntity> getAllBusinessDates() {
        return repository.findAllByOrderBySourceTagAscEntityAsc();
    }
}
