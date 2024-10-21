package com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FieldMaintenanceResponseDTO {
    private int maintenanceId;
    private Date maintenanceDate;
    private String description;
    private String status;
    private FieldResponseDTO field;
}
