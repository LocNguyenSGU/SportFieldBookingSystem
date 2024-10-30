package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingResponseDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingServiceImpl {
    public List<BookingResponseDTO> getAllBooking();
    public BookingResponseDTO getBookingByCode(String code);
    public BookingResponseDTO createNewBooking(BookingResponseDTO booking);
    public BookingResponseDTO updateBooking(int id, Booking bookingDetails);
    public boolean checkForOverlappingBookings(int bookingId, int fieldId, LocalDate bookingDate, LocalTime startTime, LocalTime endTime);
}
