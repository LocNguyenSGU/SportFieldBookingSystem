package com.example.SportFieldBookingSystem.DTO.BookingDTO;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Event {
    private int id;
    private LocalTime start;
    private LocalTime end;
    private int userId;
    private double totalPrice;
}
