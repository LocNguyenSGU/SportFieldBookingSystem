package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.Entity.Field;

import java.util.List;
import java.util.Optional;

public interface FieldService {
    List<Field> getAllField();
    void saveField(Field field);
    Optional<Field> findFieldByFieldId(Integer fieldId);
    void deleteField(Integer fieldId);
    void updateField(Field field);}
