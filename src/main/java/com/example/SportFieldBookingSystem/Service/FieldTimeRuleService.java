package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FieldTimeRuleService {
    FieldTimeRuleDTO createFieldTimeRule(FieldTimeRuleCreateDTO fieldTimeRuleCreateDTO);
    List<FieldTimeRuleDTO> getAllFieldTimeRule();
    FieldTimeRuleDTO getFieldTimeRuleById(int id);
    FieldTimeRuleDTO updateFieldTimeRule(int id, FieldTimeRuleDTO fieldTimeRuleDTO);
    boolean deleteFieldTimeRule(int id);
    Page<FieldTimeRuleDTO> getFieldTimeRuleByFieldId(int fieldId, int page, int pageSize);
    boolean isTimeSlotConflict(FieldTimeRuleCreateDTO fieldTimeRuleCreateDTO);
}
