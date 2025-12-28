package org.mupiwa.kafka.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GdrMessage {
    private int messageId;
    private boolean isEod;
    private String businessDate;
    private String sourceTag;
    private String entity;
}
