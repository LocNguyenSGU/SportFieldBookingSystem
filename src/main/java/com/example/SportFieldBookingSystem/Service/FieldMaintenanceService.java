package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO.FieldMaintenanceCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO.FieldMaintenanceResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO.FieldMaintenanceUpdateDTO;

import java.util.List;

public interface FieldMaintenanceService {
    // Create
    FieldMaintenanceResponseDTO createFieldMaintenance(FieldMaintenanceCreateDTO fieldMaintenanceCreateDTO);

    FieldMaintenanceResponseDTO updateFieldMaintenance(int id, FieldMaintenanceUpdateDTO fieldMaintenanceUpdateDTO);

    List<FieldMaintenanceResponseDTO> getAllFieldMaintenance();

    FieldMaintenanceResponseDTO getFieldMaintenanceById(int id);

    void deleteFieldMaintenanceById(int id);

}
