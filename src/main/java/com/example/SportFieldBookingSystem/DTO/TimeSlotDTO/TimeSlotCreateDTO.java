package com.example.SportFieldBookingSystem.DTO.TimeSlotDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
public class TimeSlotCreateDTO {
    private int id;
    private Date date;
    private Time startTime;
    private Time endTime;
}