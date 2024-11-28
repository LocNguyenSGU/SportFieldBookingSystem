package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceThongKeDTO;
import com.example.SportFieldBookingSystem.Entity.Invoice;

public class InvoiceMapper {
    public static InvoiceThongKeDTO fromEntity(Invoice invoice) {
        return new InvoiceThongKeDTO(
                invoice.getInvoiceId(),
                invoice.getInvoiceCode(),
                invoice.getBookingList() != null
                        ? invoice.getBookingList().stream()
                        .map(booking -> {
                            try {
                                return BookingMapper.toDTO(booking);
                            } catch (NullPointerException e) {
                                // Handle trường hợp Booking hoặc thuộc tính trong đó null
                                return null; // Hoặc throw lỗi tùy thuộc vào yêu cầu
                            }
                        })
                        .toList()
                        : null, // Trường hợp list là null
                invoice.getInvDate(),
                invoice.getName(),
                invoice.getPhoneNumber(),
                invoice.getEmail(),
                invoice.getTotalAmount(),
                invoice.getStatus()
        );
    }
}
