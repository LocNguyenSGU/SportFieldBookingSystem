package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.Exception.ResourceNotFoundException;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldListDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Repository.FieldRepository;
import com.example.SportFieldBookingSystem.Repository.FieldTypeRepository;
import com.example.SportFieldBookingSystem.Repository.LocationRepository;
import com.example.SportFieldBookingSystem.Service.FieldService;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FieldTypeRepository fieldTypeRepository;
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FieldServiceImpl(FieldRepository fieldRepository, FieldTypeRepository fieldTypeRepository, LocationRepository locationRepository, ModelMapper modelMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldTypeRepository = fieldTypeRepository;
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    // Tạo mới một Field
    @Override
    public FieldGetDTO createField(FieldCreateDTO fieldCreateDTO) {
        Field field = modelMapper.map(fieldCreateDTO, Field.class);
        field.setFieldType(fieldTypeRepository.findById(fieldCreateDTO.getFieldTypeId()).orElseThrow());
        field.setLocation(locationRepository.findById(fieldCreateDTO.getLocationId()).orElseThrow());
        field = fieldRepository.save(field);
        return modelMapper.map(field, FieldGetDTO.class);
    }

    // Lấy Field theo ID
    @Override
    public FieldGetDTO getFieldById(int fieldId) {
        Field field = fieldRepository.findById(fieldId).orElseThrow(() -> new ResourceNotFoundException("Field not found with id: " + fieldId));
//        Hibernate.initialize(field.getTimeSlotList());
        return modelMapper.map(field, FieldGetDTO.class);
    }

//     Lấy tất cả Fields
    @Override
    public List<FieldListDTO> getAllFields() {
        return fieldRepository.findAll().stream()
                .map(field -> modelMapper.map(field, FieldListDTO.class))
                .collect(Collectors.toList());
    }

    // Cập nhật Field
    @Override
    public FieldGetDTO updateField(int fieldId, FieldUpdateDTO fieldUpdateDTO) {
        Field field = fieldRepository.findById(fieldId).orElseThrow(() -> new ResourceNotFoundException("Field not found with id: " + fieldId));
        modelMapper.map(fieldUpdateDTO, field); // Map các thuộc tính từ DTO vào entity
        field.setFieldType(fieldTypeRepository.findById(fieldUpdateDTO.getFieldTypeId()).orElseThrow());
        field.setLocation(locationRepository.findById(fieldUpdateDTO.getLocationId()).orElseThrow());
        field = fieldRepository.save(field);
        return modelMapper.map(field, FieldGetDTO.class);
    }

    // Xóa Field
    @Override
    public boolean deleteField(int fieldId) {
        if (fieldRepository.existsById(fieldId)) {
            fieldRepository.deleteById(fieldId);
            return true;
        }
        return false;
    }
}
