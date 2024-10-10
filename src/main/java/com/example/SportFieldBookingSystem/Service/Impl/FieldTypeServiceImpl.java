package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.FieldType;
import com.example.SportFieldBookingSystem.Repository.FieldTypeRepository;
import com.example.SportFieldBookingSystem.Service.FieldTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.ErrorManager;

@Service
public class FieldTypeServiceImpl implements FieldTypeService {
    private final FieldTypeRepository fieldTypeRepository;

    @Autowired
    public FieldTypeServiceImpl(FieldTypeRepository fieldTypeRepository) {
        this.fieldTypeRepository = fieldTypeRepository;
    }

    @Override
    public FieldTypeResponseDTO createFieldType(FieldTypeCreateDTO fieldTypeCreateDTO) {
        if (fieldTypeCreateDTO == null) {
            throw new IllegalArgumentException("FieldTypeRequestDTO cannot be null");
        }
        try {
            // Convert DTO to Entity manually
            FieldType fieldType = new FieldType();
            fieldType.setFieldTypeName(fieldTypeCreateDTO.getFieldTypeName());
            fieldType.setFieldTypeDesc(fieldTypeCreateDTO.getFieldTypeDescription());

            // Save entity to DB
            FieldType savedFieldType = fieldTypeRepository.save(fieldType);

            // Convert Entity back to DTO manually
            FieldTypeResponseDTO resultDTO = new FieldTypeResponseDTO();
            resultDTO.setFieldTypeId(savedFieldType.getFieldTypeId());
            resultDTO.setFieldTypeName(savedFieldType.getFieldTypeName());
            resultDTO.setFieldTypeDescription(savedFieldType.getFieldTypeDesc());

            return resultDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error creating FieldType: " + e.getMessage());
        }
    }

    @Override
    public List<FieldTypeResponseDTO> getAllFieldTypes() {
        List<FieldTypeResponseDTO> fieldTypeResponseDTOList = new ArrayList<>();
        try {
            List<FieldType> fieldTypeList = fieldTypeRepository.findAll();
            for (FieldType fieldType : fieldTypeList) {
                FieldTypeResponseDTO fieldTypeResponseDTO = new FieldTypeResponseDTO();
                fieldTypeResponseDTO.setFieldTypeId(fieldType.getFieldTypeId());
                fieldTypeResponseDTO.setFieldTypeName(fieldType.getFieldTypeName());
                fieldTypeResponseDTO.setFieldTypeDescription(fieldType.getFieldTypeDesc());
                fieldTypeResponseDTOList.add(fieldTypeResponseDTO);
            }
        } catch (Exception e) {
                System.err.println("Error fetching all field types: " + e.getMessage());
        }
        return fieldTypeResponseDTOList;
    }

    @Override
    public FieldTypeResponseDTO getFieldTypeById(int id) {
        try {
            // Find by ID
            FieldType fieldType = fieldTypeRepository.findById(id).get();

            // Convert Entity to DTO manually
            FieldTypeResponseDTO fieldTypeResponseDTO = new FieldTypeResponseDTO();
            fieldTypeResponseDTO.setFieldTypeName(fieldType.getFieldTypeName());
            fieldTypeResponseDTO.setFieldTypeDescription(fieldType.getFieldTypeDesc());

            return fieldTypeResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting FieldType by ID: " + e.getMessage());
        }
    }

    @Override
    public FieldTypeResponseDTO updateFieldType(int fieldTypeId, FieldTypeUpdateDTO fieldTypeUpdateDTO) {
        try {
            FieldType fieldType = fieldTypeRepository.findById(fieldTypeId).get();

            // Update fields manually
            fieldType.setFieldTypeName(fieldTypeUpdateDTO.getFieldTypeName());
            fieldType.setFieldTypeDesc(fieldTypeUpdateDTO.getFieldTypeDescription());

            // Save updated FieldType to DB
            FieldType savedFieldType = fieldTypeRepository.save(fieldType);

            FieldTypeResponseDTO resultDTO = new FieldTypeResponseDTO();
            resultDTO.setFieldTypeId(savedFieldType.getFieldTypeId());
            resultDTO.setFieldTypeName(savedFieldType.getFieldTypeName());
            resultDTO.setFieldTypeDescription(savedFieldType.getFieldTypeDesc());

            return resultDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error updating FieldType: " + e.getMessage());
        }
    }

    @Override
    public void deleteFieldType(int id) {
        try {
            // Find existing FieldType by ID
            FieldType existingFieldType = fieldTypeRepository.findById(id).get();

            // Delete FieldType from DB
            fieldTypeRepository.delete(existingFieldType);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting FieldType: " + e.getMessage());
        }
    }
}
