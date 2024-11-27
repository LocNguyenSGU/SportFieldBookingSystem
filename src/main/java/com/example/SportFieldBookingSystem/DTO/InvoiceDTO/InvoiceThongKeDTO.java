package com.example.SportFieldBookingSystem.DTO.InvoiceDTO;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Enum.InvoiceEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceThongKeDTO {
    private int invoiceId;
    private String invoiceCode;
    private List<BookingResponseDTO> bookingList;
    private Date invDate;
    private String name;
    private String phoneNumber;
    private String email;
    private double totalAmount;
    private InvoiceEnum status; // (Pending, Paid, Cancelled)
}
