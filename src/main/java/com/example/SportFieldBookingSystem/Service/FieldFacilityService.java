package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityUpdateDTO;

import java.util.List;

public interface FieldFacilityService {
    // Create FieldFacility
    FieldFacilityResponseDTO createFieldFacility(FieldFacilityCreateDTO fieldFacilityCreateDTO);
    // Find FieldFacility by its Id
    FieldFacilityResponseDTO getFieldFacilityById(int id);
    // Get list FieldFacility
    List<FieldFacilityResponseDTO> getAllFieldFacility();
    // Update FieldFacility
    FieldFacilityResponseDTO updateFieldFacility(int id, FieldFacilityUpdateDTO fieldFacilityUpdateDTO);
    // Delete FieldFacility
    void deleteFieldFacility(int id);
}
