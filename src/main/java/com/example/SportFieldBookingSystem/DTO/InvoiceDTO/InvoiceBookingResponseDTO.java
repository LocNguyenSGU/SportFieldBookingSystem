package com.example.SportFieldBookingSystem.DTO.InvoiceDTO;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingRequestDTO;
import lombok.Data;

@Data
public class InvoiceBookingResponseDTO {
    private String name;
    private String phoneNumber;
    private String email;
    private double totalAmount;
    private BookingRequestDTO booking;
}
