package org.mupiwa.kafka.repository;

import org.mupiwa.kafka.entity.SourceEntityBusinessDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SourceEntityBusinessDateRepository extends JpaRepository<SourceEntityBusinessDateEntity, Long> {
    Optional<SourceEntityBusinessDateEntity> findBySourceTagAndEntity(String sourceTag, String entity);
    List<SourceEntityBusinessDateEntity> findAllByOrderBySourceTagAscEntityAsc();
}
