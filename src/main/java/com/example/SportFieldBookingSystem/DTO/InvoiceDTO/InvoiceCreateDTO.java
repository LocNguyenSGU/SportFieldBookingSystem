package com.example.SportFieldBookingSystem.DTO.InvoiceDTO;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingRequestDTO;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceCreateDTO {
    private String name;
    private String phoneNumber;
    private String email;
    private double totalAmount;
    private List<BookingRequestDTO> bookings;
}
