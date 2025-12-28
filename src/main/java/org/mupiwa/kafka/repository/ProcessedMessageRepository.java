package org.mupiwa.kafka.repository;

import org.mupiwa.kafka.entity.ProcessedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, Long> {
    List<ProcessedMessage> findAllByOrderByProcessedAtDesc();
}
