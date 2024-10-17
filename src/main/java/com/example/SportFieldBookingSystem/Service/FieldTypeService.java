package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeUpdateDTO;

import java.util.List;

public interface FieldTypeService {
    // Create a new FieldType
    FieldTypeResponseDTO createFieldType(FieldTypeCreateDTO fieldTypeCreateDTO);

    // Get a FieldType by its ID
    FieldTypeResponseDTO getFieldTypeById(int id);

    // Get all FieldTypes
    List<FieldTypeResponseDTO> getAllFieldTypes();

    // Update an existing FieldType
    FieldTypeResponseDTO updateFieldType(int id, FieldTypeUpdateDTO fieldTypeUpdateDTODTO);

    // Delete a FieldType by its ID
    void deleteFieldType(int id);
}
