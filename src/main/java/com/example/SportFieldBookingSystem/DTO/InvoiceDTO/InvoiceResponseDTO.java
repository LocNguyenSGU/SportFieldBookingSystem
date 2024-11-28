package com.example.SportFieldBookingSystem.DTO.InvoiceDTO;

import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Enum.InvoiceEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InvoiceResponseDTO {
    private int invoiceId;
    private String invoiceCode;
    private Date invDate;
    private Double totalAmount;
    private InvoiceEnum paymentStatus;
}
