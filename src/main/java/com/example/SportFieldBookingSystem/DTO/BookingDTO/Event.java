package com.example.SportFieldBookingSystem.DTO.BookingDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private int id;
    private LocalTime start;
    private LocalTime end;
    private int userId;
    private double totalPrice;
}
