package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.*;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Entity.Invoice;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingServiceImpl {
    public List<BookingResponseDTO> getAllBooking();
    public BookingResponseDTO getBookingByCode(String code);
    public BookingResponseDTO createNewBooking(BookingResponseDTO booking);
    public BookingResponseDTO updateBooking(int id, Booking bookingDetails);
    public boolean checkForOverlappingBookings(int bookingId, int fieldId, LocalDate bookingDate, LocalTime startTime, LocalTime endTime);
    public List<BookingRequestDTO> createBookings(BookingRequestDTO bookingRequestDTO, Invoice invoice);
    public void finalizeBooking(int bookingId);
    public void cancelBooking(int bookingId);
    List<FieldGetDTO> getBookedFields();
    List<BookingResponseDTO> getBookedFieldsWithTime();
    List<RevenueDTO> getRevenueStatistics();
    List<QuarterlyRevenueDTO> getRevenueStatisticsByQuarter();
    List<MostBookedFieldDTO> getMostBookedFields();

}
