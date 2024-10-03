package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;

import java.util.List;

public interface BookingServiceImpl {
    public List<BookingResponseDTO> getAllBooking();
    public BookingResponseDTO getBookingByCode(String code);
    public BookingResponseDTO createNewBooking(Booking booking);
    public BookingResponseDTO updateBooking(int id, Booking bookingDetails);
}
