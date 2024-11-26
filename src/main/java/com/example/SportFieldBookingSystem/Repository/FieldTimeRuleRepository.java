package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleDTO;
import com.example.SportFieldBookingSystem.Entity.FieldTimeRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldTimeRuleRepository extends JpaRepository<FieldTimeRule, Integer> {
    Page<FieldTimeRule> findAllByFieldFieldId(int fieldId, Pageable pageable);
}
