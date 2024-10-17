package com.example.SportFieldBookingSystem.DTO.FieldMaintenanceDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class FieldMaintenanceUpdateDTO {
    private Date maintenanceDate;
    private String description;
    private String status;
}
