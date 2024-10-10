package com.example.SportFieldBookingSystem.DTO.FieldImageDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FieldImageResponseDTO {
    private int fieldImageId;
    private int fieldId;
    private String fieldImageUrl;
}
