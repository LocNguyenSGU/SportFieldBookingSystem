package com.example.SportFieldBookingSystem.DTO.FieldTypeDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FieldTypeResponseDTO {
    private int fieldTypeId;
    private String fieldTypeName;
    private String fieldTypeDescription;
}
