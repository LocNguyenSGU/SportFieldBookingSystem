package com.example.SportFieldBookingSystem.DTO.BookingDTO;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Event {
    private LocalTime start;
    private LocalTime end;
    private double totalPrice;
}
