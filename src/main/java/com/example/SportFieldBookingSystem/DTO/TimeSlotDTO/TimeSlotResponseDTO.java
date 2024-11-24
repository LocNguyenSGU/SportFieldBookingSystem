package com.example.SportFieldBookingSystem.DTO.TimeSlotDTO;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldResponseDTO;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Date;

@Data
public class TimeSlotResponseDTO {
    private int timeslotId;
    private Date date;
    private Time startTime;
    private Time endTime;
}
