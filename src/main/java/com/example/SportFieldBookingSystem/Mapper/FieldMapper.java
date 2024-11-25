package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldListDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleDTO;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeResponseDTO;
import com.example.SportFieldBookingSystem.Entity.FieldTimeRule;

public class FieldMapper {

    public static FieldListDTO mapToFieldListDTO(Field field) {
        FieldListDTO dto = new FieldListDTO();

        dto.setFieldId(field.getFieldId());
        dto.setFieldCode(field.getFieldCode());
        dto.setFieldName(field.getFieldName());
        dto.setCapacity(field.getCapacity());
        dto.setPricePerHour(field.getPricePerHour());
        dto.setStatus(field.getStatus() != null ? field.getStatus().name() : null);

        // Mapping FieldType to FieldTypeResponseDTO
        if (field.getFieldType() != null) {
            FieldTypeResponseDTO fieldTypeDTO = new FieldTypeResponseDTO();
            fieldTypeDTO.setFieldTypeId(field.getFieldType().getFieldTypeId()); // Assuming getId exists
            fieldTypeDTO.setFieldTypeName(field.getFieldType().getFieldTypeName()); // Assuming getName exists
            dto.setFieldType(fieldTypeDTO);
        }

        return dto;
    }

    public static FieldTimeRuleDTO mapToFieldTimeRuleDTO(FieldTimeRule entity) {
        FieldTimeRuleDTO dto = new FieldTimeRuleDTO();
        dto.getField().setFieldId(entity.getId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDaysOfWeek(entity.getDaysOfWeek());
        return dto;
    }

}
