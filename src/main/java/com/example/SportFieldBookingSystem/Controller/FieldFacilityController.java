package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityUpdateDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.FieldFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
@RestController
@RequestMapping("/fieldFacility")
public class FieldFacilityController {
    private final FieldFacilityService fieldFacilityService;

    @Autowired
    public FieldFacilityController(FieldFacilityService fieldFacilityService) {
        this.fieldFacilityService = fieldFacilityService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFieldFacility(@RequestBody FieldFacilityCreateDTO fieldFacilityCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            FieldFacilityResponseDTO fieldFacilityResponseDTO = fieldFacilityService.createFieldFacility(fieldFacilityCreateDTO);
            if (fieldFacilityResponseDTO == null) {
                responseData.setMessage("Field facility could not be created");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
            responseData.setData(fieldFacilityResponseDTO);
            responseData.setMessage("Field facility created successfully");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFieldFacility(@PathVariable int id,
                                                        @RequestBody FieldFacilityUpdateDTO fieldFacilityUpdateDTO){
        ResponseData responseData = new ResponseData();
        try {
            FieldFacilityResponseDTO responseDTO = fieldFacilityService.updateFieldFacility(id, fieldFacilityUpdateDTO);
            if (responseDTO == null) {
                responseData.setMessage("Field facility could not be updated");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
            responseData.setData(responseDTO);
            responseData.setMessage("Field facility updated successfully");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (RuntimeException e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        ResponseData responseData = new ResponseData();
        try {
            List<FieldFacilityResponseDTO> fieldFacs = fieldFacilityService.getAllFieldFacility();
            if (fieldFacs.isEmpty()) {
                responseData.setMessage("No field facility found");
                return new ResponseEntity<>(responseData, HttpStatus.NO_CONTENT);
            }
            responseData.setData(fieldFacs);
            responseData.setMessage("All field facility found");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFieldFacility(@PathVariable int id){
        ResponseData responseData = new ResponseData();
        try {
            FieldFacilityResponseDTO fieldFacilityResponseDTO = fieldFacilityService.getFieldFacilityById(id);
            if (fieldFacilityResponseDTO == null) {
                responseData.setMessage("Field facility could not be found");
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
            responseData.setData(fieldFacilityResponseDTO);
            responseData.setMessage("Field facility found");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFieldFacility(@PathVariable int id){
        ResponseData responseData = new ResponseData();
        try {
            fieldFacilityService.deleteFieldFacility(id);
            responseData.setMessage("Field facility deleted successfully");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (RuntimeException e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
