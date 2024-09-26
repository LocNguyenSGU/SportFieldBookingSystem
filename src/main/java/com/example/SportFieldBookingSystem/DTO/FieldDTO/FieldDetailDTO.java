package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class FieldDetailDTO {
    private int fieldId;
    private String fieldCode;
    private String fieldName;
    private int capacity;
    private double pricePerHour;

//    private FieldTypeDTO fieldType;
//    private LocationDTO location;
}