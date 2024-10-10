package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO.FieldMaintenanceCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO.FieldMaintenanceResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO.FieldMaintenanceUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.FieldMaintenance;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.FieldMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fieldMaintenance")
public class FieldMaintenanceController {
    private final FieldMaintenanceService fieldMaintenanceService;

    @Autowired
    public FieldMaintenanceController(FieldMaintenanceService fieldMaintenanceService) {
        this.fieldMaintenanceService = fieldMaintenanceService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFieldMaintenance(@RequestBody FieldMaintenanceCreateDTO fieldMaintenance) {
        ResponseData responseData = new ResponseData();
        try {
            FieldMaintenanceResponseDTO fieldMaintenanceResponseDTO = fieldMaintenanceService.createFieldMaintenance(fieldMaintenance);
            if (fieldMaintenanceResponseDTO != null) {
                responseData.setMessage("Successfully created field maintenance");
                responseData.setData(fieldMaintenanceResponseDTO);
                return ResponseEntity.ok(responseData);
            }
            responseData.setMessage("Failed to create field maintenance");
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("Failed to create field maintenance");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFieldMaintenance(@RequestBody FieldMaintenanceUpdateDTO fieldMaintenance, @PathVariable int id) {
        ResponseData responseData = new ResponseData();
        try {
            FieldMaintenanceResponseDTO fieldMaintenanceResponseDTO = fieldMaintenanceService.updateFieldMaintenance(id, fieldMaintenance);
            if (fieldMaintenanceResponseDTO != null) {
                responseData.setMessage("Successfully updated field maintenance");
                responseData.setData(fieldMaintenanceResponseDTO);
                return ResponseEntity.ok(responseData);
            }
            responseData.setMessage("Failed to update field maintenance");
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("Failed to update field maintenance");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllFieldMaintenance() {
        ResponseData responseData = new ResponseData();
        try {
            List<FieldMaintenanceResponseDTO> fieldMaintenanceList = fieldMaintenanceService.getAllFieldMaintenance();
            if (fieldMaintenanceList != null) {
                responseData.setMessage("Successfully retrieved field maintenance");
                responseData.setData(fieldMaintenanceList);
                return ResponseEntity.ok(responseData);
            }
            responseData.setMessage("Failed to retrieve field maintenance");
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("Failed to retrieve field maintenance");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFieldMaintenance(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        try {
            FieldMaintenanceResponseDTO fieldMaintenanceResponseDTO = fieldMaintenanceService.getFieldMaintenanceById(id);
            if (fieldMaintenanceResponseDTO != null) {
                responseData.setMessage("Successfully retrieved field maintenance");
                responseData.setData(fieldMaintenanceResponseDTO);
                return ResponseEntity.ok(responseData);
            }
            responseData.setMessage("Failed to retrieve field maintenance");
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("Failed to retrieve field maintenance");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFieldMaintenance(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        try {
            fieldMaintenanceService.deleteFieldMaintenanceById(id);
            responseData.setMessage("Successfully deleted field maintenance");
            responseData.setData(null);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setMessage("Failed to delete field maintenance");
            responseData.setData(e);
            return ResponseEntity.ok(responseData);
        }
    }
}
