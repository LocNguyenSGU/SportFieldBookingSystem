package com.example.SportFieldBookingSystem.DTO.InvoiceDTO;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingRequestDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceRequestDTO {
    private String name;
    private String phoneNumber;
    private String email;
    private double totalAmount;
    private BookingRequestDTO booking;
}
