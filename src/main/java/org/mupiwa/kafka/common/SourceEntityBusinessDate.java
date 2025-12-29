package org.mupiwa.kafka.common;

public class SourceEntityBusinessDate {
    private String sourceTag;
    private String entity;
    private String businessDate;

    public SourceEntityBusinessDate() {
    }

    public SourceEntityBusinessDate(String sourceTag, String entity, String businessDate) {
        this.sourceTag = sourceTag;
        this.entity = entity;
        this.businessDate = businessDate;
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

}
