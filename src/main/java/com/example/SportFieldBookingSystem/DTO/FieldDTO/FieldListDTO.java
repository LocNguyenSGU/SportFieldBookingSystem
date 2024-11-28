package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeResponseDTO;
import com.example.SportFieldBookingSystem.Entity.FieldType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FieldListDTO {
    private int fieldId;
    private String fieldCode;
    private String fieldName;
    private int capacity;
    private double pricePerHour;
    private String status;
//    private String location;
    private FieldTypeResponseDTO fieldType;
}
