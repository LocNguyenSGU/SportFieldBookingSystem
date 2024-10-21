package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;

public class BookingMapper {
    public static BookingResponseDTO toDTO(Booking booking){
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setBookingId(booking.getBookingId());
        bookingResponseDTO.setUser(booking.getUser().getUserId());
        bookingResponseDTO.setField(booking.getField());
        bookingResponseDTO.setBookingCode(booking.getBookingCode());
        bookingResponseDTO.setStartTime(booking.getStartTime());
        bookingResponseDTO.setEndTime(booking.getEndTime());
        bookingResponseDTO.setInvoice(booking.getInvoice().getInvoiceId());
        return bookingResponseDTO;
    }
}
