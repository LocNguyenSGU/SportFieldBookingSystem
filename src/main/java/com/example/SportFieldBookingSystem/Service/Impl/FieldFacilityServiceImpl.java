package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.FieldFacility;
import com.example.SportFieldBookingSystem.Repository.FieldFacilityRepository;
import com.example.SportFieldBookingSystem.Service.FieldFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FieldFacilityServiceImpl implements FieldFacilityService {
    @Autowired
    private FieldFacilityRepository fieldFacilityRepository;

    // Create FieldFacility
    public FieldFacilityResponseDTO createFieldFacility(FieldFacilityCreateDTO fieldFacilityCreateDTO) {
        if (fieldFacilityCreateDTO == null) {
            throw new IllegalArgumentException("FieldTypeCreateDTO cannot be null");
        }
        try {
            // Convert DTO to Entity
            FieldFacility fieldFac = new FieldFacility();
            fieldFac.setFacilityName(fieldFacilityCreateDTO.getFacilityName());

            // Save entity to DB
            FieldFacility fieldFacSave = fieldFacilityRepository.save(fieldFac);

            // Convert DTO back to Entity
            FieldFacilityResponseDTO fieldFacRs = new FieldFacilityResponseDTO();
            fieldFacRs.setFieldFacilityId(fieldFacSave.getFacilityId());
            fieldFacRs.setFieldFacilityName(fieldFacSave.getFacilityName());
            return fieldFacRs;
        } catch (Exception e) {
            throw new RuntimeException("Error creating FieldFacility", e);
        }
    }
    // Find FieldFacility by its Id
    public FieldFacilityResponseDTO getFieldFacilityById(int id) {
        try {
            FieldFacility fieldFacGet = fieldFacilityRepository.findById(id).get();

            FieldFacilityResponseDTO fieldFacRs = new FieldFacilityResponseDTO();
            fieldFacRs.setFieldFacilityName(fieldFacGet.getFacilityName());
            fieldFacRs.setFieldFacilityId(fieldFacGet.getFacilityId());

            return fieldFacRs;
        } catch (Exception e) {
            throw new RuntimeException("Error getting FieldFacility", e);
        }
    }
    // Get list FieldFacility
    public List<FieldFacilityResponseDTO> getAllFieldFacility(){
        List<FieldFacilityResponseDTO> fieldFacs = new ArrayList<>();
        try {
            List<FieldFacility> fieldFacList = fieldFacilityRepository.findAll();
            for (FieldFacility fieldFac : fieldFacList) {
                FieldFacilityResponseDTO fieldFacRs = new FieldFacilityResponseDTO();
                fieldFacRs.setFieldFacilityId(fieldFac.getFacilityId());
                fieldFacRs.setFieldFacilityName(fieldFac.getFacilityName());
                fieldFacs.add(fieldFacRs);
            }
            return fieldFacs;
        } catch (Exception e) {
            throw new RuntimeException("Error getting FieldFacility", e);
        }
    }
    // Update FieldFacility
    public FieldFacilityResponseDTO updateFieldFacility(int id, FieldFacilityUpdateDTO fieldFacilityUpdateDTO) {
        try {
            FieldFacility fieldFacility = fieldFacilityRepository.findById(id).get();

            fieldFacility.setFacilityName(fieldFacilityUpdateDTO.getFacilityName());
            FieldFacility fieldFacSave = fieldFacilityRepository.save(fieldFacility);

            FieldFacilityResponseDTO fieldFacRs = new FieldFacilityResponseDTO();
            fieldFacRs.setFieldFacilityId(fieldFacSave.getFacilityId());
            fieldFacRs.setFieldFacilityName(fieldFacSave.getFacilityName());
            return fieldFacRs;
        } catch (Exception e) {
            throw new RuntimeException("Error updating FieldFacility", e);
        }
    }
    // Delete FieldFacility
    public void deleteFieldFacility(int id) {
        try {
            FieldFacility fieldFacility = fieldFacilityRepository.findById(id).get();

            fieldFacilityRepository.delete(fieldFacility);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting FieldFacility", e);
        }
    }
}
