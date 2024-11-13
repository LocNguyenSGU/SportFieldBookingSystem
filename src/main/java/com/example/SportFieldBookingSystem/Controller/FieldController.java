package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldListDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldUpdateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityResponseDTO;
import com.example.SportFieldBookingSystem.Service.FieldService;
import org.modelmapper.internal.bytebuddy.description.field.FieldList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/fields")
public class FieldController {
    private final FieldService fieldService;
    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping
    public ResponseEntity<?> createField(@RequestBody FieldCreateDTO fieldCreateDTO) throws URISyntaxException {
        FieldGetDTO savedDTO = fieldService.createField(fieldCreateDTO);
        return ResponseEntity.created(new URI("/api/fields" + savedDTO.getFieldId())).body(savedDTO);
    }

    @GetMapping
    public List<FieldListDTO> getAllFields() {
        return fieldService.getAllFields();
    }

    @GetMapping("/{id}")
    public FieldGetDTO getFieldById(@PathVariable int id) {
        return fieldService.getFieldById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteFieldById(@PathVariable int id) {
        return fieldService.deleteField(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFieldById(@PathVariable int id, @RequestBody FieldUpdateDTO fieldUpdateDTO) {
        FieldGetDTO updatedDTO = fieldService.updateField(id, fieldUpdateDTO);
        return ResponseEntity.ok(updatedDTO);
    }
}
