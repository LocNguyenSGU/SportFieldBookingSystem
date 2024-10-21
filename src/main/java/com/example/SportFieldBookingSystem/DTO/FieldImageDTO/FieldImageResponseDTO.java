package com.example.SportFieldBookingSystem.DTO.FieldImageDTO;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FieldImageResponseDTO {
    private int fieldImageId;
    private String fieldImageURL;
    private FieldResponseDTO field;
}
