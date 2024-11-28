package com.example.SportFieldBookingSystem.DTO.BookingDTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookingRequestDTO {

    private int userId;
    private int fieldId;
    private LocalDate date;
    private List<Event> selectedEvents; // List of selected events

    // Getters and setters
}