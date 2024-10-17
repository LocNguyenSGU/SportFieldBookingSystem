package com.example.SportFieldBookingSystem.DTO.LocationDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LocationResponseDTO {
    private int locationId;
    private String locationNumber;
    private int streetId;
}
