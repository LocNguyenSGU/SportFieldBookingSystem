package com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FieldFacilityResponse {
    private int fieldFacilityId;
    private String fieldFacilityName;
    private FieldResponseDTO fieldResponseDTO;
}
