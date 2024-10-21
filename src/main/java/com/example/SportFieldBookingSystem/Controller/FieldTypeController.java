package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeUpdateDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SportFieldBookingSystem.Service.FieldTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/fieldType")
public class FieldTypeController {
    private final FieldTypeService fieldTypeService;

    @Autowired
    public FieldTypeController(FieldTypeService fieldTypeService) {
        this.fieldTypeService = fieldTypeService;
    }

    // Tạo mới FieldType
    @PostMapping("/create")
    public ResponseEntity<?> createFieldType(@RequestBody FieldTypeCreateDTO fieldTypeCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            FieldTypeResponseDTO createdFieldType = fieldTypeService.createFieldType(fieldTypeCreateDTO);
            if (createdFieldType == null) {
                responseData.setMessage("FieldType not created");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }

            responseData.setData(createdFieldType);
            responseData.setMessage("FieldType created successfully");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cập nhật FieldType
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFieldType(@PathVariable int id, @RequestBody FieldTypeUpdateDTO fieldTypeUpdateDTO) {
        try {
            ResponseData responseData = new ResponseData();

            FieldTypeResponseDTO updatedFieldType = fieldTypeService.updateFieldType(id, fieldTypeUpdateDTO);
            if (updatedFieldType == null) {
                responseData.setMessage("FieldType not updated");
                return new ResponseEntity<>(responseData, HttpStatus.NO_CONTENT);
            }
            responseData.setData(updatedFieldType);
            responseData.setMessage("FieldType updated successfully");
            return new ResponseEntity<>(updatedFieldType, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy FieldType theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFieldTypeById(@PathVariable int id) {
        try {
            ResponseData responseData = new ResponseData();

            FieldTypeResponseDTO fieldType = fieldTypeService.getFieldTypeById(id);
            if (fieldType == null) {
                responseData.setMessage("FieldType not found");
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
            responseData.setData(fieldType);
            responseData.setMessage("FieldType found successfully");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy tất cả FieldTypes
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllFieldTypes() {
        try {
            ResponseData responseData = new ResponseData();

            List<FieldTypeResponseDTO> fieldTypes = fieldTypeService.getAllFieldTypes();
            if (fieldTypes.isEmpty()) {
                responseData.setMessage("No field types found");
                return new ResponseEntity<>(responseData ,HttpStatus.NO_CONTENT);
            }
            responseData.setData(fieldTypes);
            responseData.setMessage("All field types found");
            return new ResponseEntity<>(fieldTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Xóa FieldType
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFieldType(@PathVariable Integer id) {
        try {
            ResponseData responseData = new ResponseData();

            fieldTypeService.deleteFieldType(id);
            responseData.setMessage("FieldType deleted");
            return new ResponseEntity<>(responseData, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
