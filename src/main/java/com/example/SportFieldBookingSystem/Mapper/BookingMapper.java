package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Entity.Invoice;
import com.example.SportFieldBookingSystem.Entity.User;

public class BookingMapper {
    public static BookingResponseDTO toDTO(Booking booking){
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setBookingId(booking.getBookingId());
        bookingResponseDTO.setUser(booking.getUser().getUserId());
        bookingResponseDTO.setField(booking.getField().getFieldId());
        bookingResponseDTO.setFieldName(booking.getField().getFieldName());
        bookingResponseDTO.setStatus(booking.getStatus());
        bookingResponseDTO.setTotalPrice(booking.getTotalPrice());
        bookingResponseDTO.setBookingDate(booking.getBookingDate());
        bookingResponseDTO.setBookingCode(booking.getBookingCode());
        bookingResponseDTO.setStartTime(booking.getStartTime());
        bookingResponseDTO.setEndTime(booking.getEndTime());
        bookingResponseDTO.setInvoice(booking.getInvoice().getInvoiceId());
        return bookingResponseDTO;
    }

    public static Booking toEntity(BookingResponseDTO bookingResponseDTO) {
        Booking booking = new Booking();
        booking.setBookingId(bookingResponseDTO.getBookingId());

        User user = new User();
        user.setUserId(bookingResponseDTO.getUser());
        booking.setUser(user);

//        Field field = new Field();
//        field.setFieldId(bookingResponseDTO.getField());
//        booking.setField(field);

        booking.setBookingCode(bookingResponseDTO.getBookingCode());
        booking.setStartTime(bookingResponseDTO.getStartTime());
        booking.setEndTime(bookingResponseDTO.getEndTime());

//        Invoice invoice = new Invoice();
//        invoice.setInvoiceId(bookingResponseDTO.getInvoice());
//        booking.setInvoice(invoice);

        return booking;
    }

}
