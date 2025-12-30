package org.mupiwa.kafka.common;

public class GdrMessage {
    private int messageId;
    private boolean isEod;
    private String businessDate;
    private String sourceTag;
    private String entity;
    private double amount;
    private String currency;

    public GdrMessage() {
    }
    public GdrMessage(int messageId, boolean isEod, String businessDate, String sourceTag, String entity, double amount, String currency)
    {
        this.messageId = messageId;
        this.isEod = isEod;
        this.businessDate = businessDate;
        this.sourceTag = sourceTag;
        this.entity = entity;
        this.amount = amount;
        this.currency = currency;
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
    public void setSourceTag(String sourceTag) {}
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

}
