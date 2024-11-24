package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleDTO;
import org.springframework.stereotype.Service;

public interface FieldTimeRuleService {
    public FieldTimeRuleDTO createFieldTimeRule(FieldTimeRuleDTO fieldTimeRuleDTO);
}
