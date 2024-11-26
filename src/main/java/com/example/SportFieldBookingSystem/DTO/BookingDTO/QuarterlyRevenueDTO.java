package com.example.SportFieldBookingSystem.DTO.BookingDTO;

import lombok.Data;

@Data
public class QuarterlyRevenueDTO {
    private int quarter;   // Quý (1-4)
    private int year;      // Năm
    private double totalRevenue;  // Tổng doanh thu

    // Constructor có tham số
    public QuarterlyRevenueDTO(int quarter, int year, double totalRevenue) {
        this.quarter = quarter;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }
}