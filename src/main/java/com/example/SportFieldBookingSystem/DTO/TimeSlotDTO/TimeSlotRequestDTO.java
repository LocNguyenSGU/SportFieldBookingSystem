package com.example.SportFieldBookingSystem.DTO.TimeSlotDTO;

import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class TimeSlotRequestDTO {
    private int id;
    private Date date;
    private Time startTime;
    private Time endTime;
    private TimeSlotEnum status;
    private int userId;
}
