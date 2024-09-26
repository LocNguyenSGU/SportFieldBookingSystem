package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Repository.FieldRepository;
import com.example.SportFieldBookingSystem.Service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class FieldServiceImpl implements FieldService {
    @Autowired private FieldRepository fieldRepository;

    @Override
    public List<Field> getAllField() {
        return (List<Field>) fieldRepository.findAll();
    }

    @Override
    public void saveField(Field field) {
        fieldRepository.save(field);
    }

    @Override
    public Optional<Field> findFieldByFieldId(Integer fieldId) {
        return fieldRepository.findById(fieldId);
    }

    @Override
    public void deleteField(Integer fieldId) {
        fieldRepository.deleteById(fieldId);
    }

    @Override
    public void updateField(Field field) {
        fieldRepository.save(field);
    }
}
