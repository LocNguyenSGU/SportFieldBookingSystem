package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO.FieldMaintenanceCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO.FieldMaintenanceResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO.FieldMaintenanceUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.FieldMaintenance;
import com.example.SportFieldBookingSystem.Enum.FieldMaintenanceEnum;
import com.example.SportFieldBookingSystem.Repository.FieldMaintenanceRepository;
import com.example.SportFieldBookingSystem.Service.FieldMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FieldMaintenanceServiceImpl implements FieldMaintenanceService {
    private final FieldMaintenanceRepository fieldMaintenanceRepository;

    @Autowired
    public FieldMaintenanceServiceImpl(FieldMaintenanceRepository fieldMaintenanceRepository) {
        this.fieldMaintenanceRepository = fieldMaintenanceRepository;
    }

    // create
    public FieldMaintenanceResponseDTO createFieldMaintenance(FieldMaintenanceCreateDTO FieldMaintenanceCreateDTO) {
        try {
            FieldMaintenance FieldMaintenance = new FieldMaintenance();
            FieldMaintenance.setMaintenanceDate(FieldMaintenanceCreateDTO.getMaintenanceDate());
            FieldMaintenance.setDescription(FieldMaintenanceCreateDTO.getDescription());
            FieldMaintenance.setStatus(FieldMaintenanceEnum.valueOf(FieldMaintenanceCreateDTO.getStatus()));

            FieldMaintenance fieldMaintenanceSave = fieldMaintenanceRepository.save(FieldMaintenance);

            FieldMaintenanceResponseDTO fieldMaintenanceResponseDTO = new FieldMaintenanceResponseDTO();
            fieldMaintenanceResponseDTO.setMaintenanceId(fieldMaintenanceSave.getMaintenanceId());
            fieldMaintenanceResponseDTO.setDescription(fieldMaintenanceSave.getDescription());
            fieldMaintenanceResponseDTO.setMaintenanceDate(fieldMaintenanceSave.getMaintenanceDate());
            fieldMaintenanceResponseDTO.setStatus(String.valueOf(fieldMaintenanceSave.getStatus()));
            return fieldMaintenanceResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error creating field maintenance");
        }
    }

    // update
    public FieldMaintenanceResponseDTO updateFieldMaintenance(int id, FieldMaintenanceUpdateDTO FieldMaintenanceUpdateDTO) {
        try {
            FieldMaintenance FieldMaintenance = fieldMaintenanceRepository.findById(id).get();

            FieldMaintenance.setMaintenanceDate(FieldMaintenanceUpdateDTO.getMaintenanceDate());
            FieldMaintenance.setDescription(FieldMaintenanceUpdateDTO.getDescription());
            FieldMaintenance.setStatus(FieldMaintenanceEnum.valueOf(FieldMaintenanceUpdateDTO.getStatus()));

            FieldMaintenance fieldMaintenanceSave = fieldMaintenanceRepository.save(FieldMaintenance);
            FieldMaintenanceResponseDTO fieldMaintenanceResponseDTO = new FieldMaintenanceResponseDTO();
            fieldMaintenanceResponseDTO.setMaintenanceId(fieldMaintenanceSave.getMaintenanceId());
            fieldMaintenanceResponseDTO.setDescription(fieldMaintenanceSave.getDescription());
            fieldMaintenanceResponseDTO.setMaintenanceDate(fieldMaintenanceSave.getMaintenanceDate());
            fieldMaintenanceResponseDTO.setStatus(String.valueOf(fieldMaintenanceSave.getStatus()));
            return fieldMaintenanceResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error updating field maintenance");
        }
    }

    // read all
    public List<FieldMaintenanceResponseDTO> getAllFieldMaintenance() {
        List<FieldMaintenanceResponseDTO> fieldMaintenanceResponseDTOList = new ArrayList<>();
        try {
            List<FieldMaintenance> fieldMaintenanceList = fieldMaintenanceRepository.findAll();
            for (FieldMaintenance fieldMaintenance : fieldMaintenanceList) {
                FieldMaintenanceResponseDTO fieldMaintenanceResponseDTO = new FieldMaintenanceResponseDTO();
                fieldMaintenanceResponseDTO.setMaintenanceId(fieldMaintenance.getMaintenanceId());
                fieldMaintenanceResponseDTO.setDescription(fieldMaintenance.getDescription());
                fieldMaintenanceResponseDTO.setMaintenanceDate(fieldMaintenance.getMaintenanceDate());
                fieldMaintenanceResponseDTO.setStatus(String.valueOf(fieldMaintenance.getStatus()));
                fieldMaintenanceResponseDTOList.add(fieldMaintenanceResponseDTO);
            }
            return fieldMaintenanceResponseDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all field maintenance");
        }
    }

    // read one
    public FieldMaintenanceResponseDTO getFieldMaintenanceById(int id) {
        FieldMaintenanceResponseDTO fieldMaintenanceResponseDTO = new FieldMaintenanceResponseDTO();
        try {
            FieldMaintenance fieldMaintenance = fieldMaintenanceRepository.findById(id).get();
            fieldMaintenanceResponseDTO.setMaintenanceId(fieldMaintenance.getMaintenanceId());
            fieldMaintenanceResponseDTO.setDescription(fieldMaintenance.getDescription());
            fieldMaintenanceResponseDTO.setMaintenanceDate(fieldMaintenance.getMaintenanceDate());
            fieldMaintenanceResponseDTO.setStatus(String.valueOf(fieldMaintenance.getStatus()));
            return fieldMaintenanceResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting field maintenance");
        }
    }

    // delete
    public void deleteFieldMaintenanceById(int id) {
        try {
            FieldMaintenance FieldMaintenance = fieldMaintenanceRepository.findById(id).get();
            fieldMaintenanceRepository.delete(FieldMaintenance);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting field maintenance");
        }
    }
}
