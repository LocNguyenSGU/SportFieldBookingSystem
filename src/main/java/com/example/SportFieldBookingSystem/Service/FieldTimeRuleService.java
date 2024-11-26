package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleDTO;

import java.util.List;

public interface FieldTimeRuleService {
    FieldTimeRuleDTO createFieldTimeRule(FieldTimeRuleDTO fieldTimeRuleDTO);
    List<FieldTimeRuleDTO> getAllFieldTimeRule();
    FieldTimeRuleDTO getFieldTimeRuleById(int id);
    FieldTimeRuleDTO updateFieldTimeRule(int id, FieldTimeRuleDTO fieldTimeRuleDTO);
    boolean deleteFieldTimeRule(int id);
}
