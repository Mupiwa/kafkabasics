package org.mupiwa.kafka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.time.LocalDateTime;

@Entity
@Table(name = "processed_messages")

public class ProcessedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int messageId;
    private boolean isEod;
    private String businessDate;
    private String sourceTag;
    private String entity;
    private double amount;
    private String currency;
    private LocalDateTime processedAt;

    public ProcessedMessage() {
    }
    public ProcessedMessage(int messageId, boolean isEod, String businessDate, String sourceTag, String entity, double amount, String currency, LocalDateTime processedAt) {
        this.messageId = messageId;
        this.isEod = isEod;
        this.businessDate = businessDate;
        this.sourceTag = sourceTag;
        this.entity = entity;
        this.amount = amount;
        this.currency = currency;
        this.processedAt = processedAt;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getMessageId() {
        return messageId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    public boolean isEod() {
        return isEod;
    }
    public void setEod(boolean eod) {
        isEod = eod;
    }
    public String getBusinessDate() {
        return businessDate;
    }
    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }
    public String getSourceTag() {
        return sourceTag;
    }
    public void setSourceTag(String sourceTag) {
        this.sourceTag = sourceTag;
    }
    public String getEntity() {
        return entity;
    }
    public void setEntity(String entity) {
       this.entity = entity;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public LocalDateTime getProcessedAt() {
        return processedAt;
    }
    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

}
