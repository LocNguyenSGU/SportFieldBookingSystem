package com.example.SportFieldBookingSystem.DTO.BookingDTO;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class RevenueDTO {
    private int month;
    private int year;
    private double totalRevenue;

    // Constructor có tham số
    public RevenueDTO(int month, int year, double totalRevenue) {
        this.month = month;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }
}
