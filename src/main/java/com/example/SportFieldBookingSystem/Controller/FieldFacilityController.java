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


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@RestController
@RequestMapping("/api/facilities")
public class FieldFacilityController {
    private final FieldFacilityService fieldFacilityService;

    @Autowired
    public FieldFacilityController(FieldFacilityService fieldFacilityService) {
        this.fieldFacilityService = fieldFacilityService;
    }

    @PostMapping
    public ResponseEntity<?> createFieldFacility(@RequestBody FieldFacilityCreateDTO fieldFacilityCreateDTO) throws URISyntaxException {
        FieldFacilityResponseDTO savedDTO = fieldFacilityService.createFieldFacility(fieldFacilityCreateDTO);
        return ResponseEntity.created(new URI("/api/fieldFacilities/" + savedDTO.getFieldFacilityId())).body(savedDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFieldFacility(@PathVariable int id,
                                                        @RequestBody FieldFacilityUpdateDTO fieldFacilityUpdateDTO){
        FieldFacilityResponseDTO updatedDTO = fieldFacilityService.updateFieldFacility(id, fieldFacilityUpdateDTO);
        return ResponseEntity.ok(updatedDTO);
    }
    @GetMapping
    public List<FieldFacilityResponseDTO> getAll(){
        return fieldFacilityService.getAllFieldFacility();
    }

    @GetMapping("/{id}")
    public FieldFacilityResponseDTO getFieldFacility(@PathVariable int id){
        return fieldFacilityService.getFieldFacilityById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFieldFacility(@PathVariable int id){
        fieldFacilityService.deleteFieldFacility(id);
        return ResponseEntity.ok().build();
    }
}
