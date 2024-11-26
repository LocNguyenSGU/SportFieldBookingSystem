package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.FieldTimeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldTimeRuleRepository extends JpaRepository<FieldTimeRule, Integer> {
}
