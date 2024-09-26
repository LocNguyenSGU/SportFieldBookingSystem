package com.example.SportFieldBookingSystem.DTO.FieldImageDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class FieldImageRequestDTO {
    private int fieldId;
    private String imageUrl;
}
