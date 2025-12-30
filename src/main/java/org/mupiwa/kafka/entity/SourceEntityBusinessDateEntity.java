package org.mupiwa.kafka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "source_entity_business_date", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sourceTag", "entity"})
})
public class SourceEntityBusinessDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sourceTag;
    private String entity;
    private String businessDate;

    public SourceEntityBusinessDateEntity() {
    }

    public SourceEntityBusinessDateEntity(String sourceTag, String entity, String businessDate) {
        this.sourceTag = sourceTag;
        this.entity = entity;
        this.businessDate = businessDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }
}
