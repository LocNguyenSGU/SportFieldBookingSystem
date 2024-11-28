package com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FieldTimeRuleDTO {
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private FieldGetDTO field;
    private String daysOfWeek; // Example: "Monday,Wednesday,Friday"
}
