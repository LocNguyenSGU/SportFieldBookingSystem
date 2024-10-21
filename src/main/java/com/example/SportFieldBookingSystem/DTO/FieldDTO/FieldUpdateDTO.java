package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class FieldUpdateDTO {
    private String fieldName;
    private int capacity;
    private double pricePerHour;
    private int fieldTypeId;
    private int locationId;
}
