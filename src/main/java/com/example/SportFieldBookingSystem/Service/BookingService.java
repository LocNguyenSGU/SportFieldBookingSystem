package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingResponseDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Entity.Invoice;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.BookingRepository;
import com.example.SportFieldBookingSystem.Repository.InvoiceRepository;
import com.example.SportFieldBookingSystem.Service.Impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService implements BookingServiceImpl {

    @Autowired
    private final BookingRepository bookingRepo;
    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    public BookingService(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }
    @Override
    public boolean checkForOverlappingBookings(int bookingId, int fieldId, LocalDate bookingDate, LocalTime startTime, LocalTime endTime) {
        List<Booking> overlappingBookings = bookingRepo.findOverlappingBookings(
                fieldId,
                bookingId,
                bookingDate,
                startTime,
                endTime
        );
        return !overlappingBookings.isEmpty();
    }
    @Override
    public List<BookingResponseDTO> getAllBooking() {
        List<BookingResponseDTO> listBookingDTO = new ArrayList<>();

        try {
            List<Booking> listBookings = bookingRepo.findAll();
            for (Booking booking : listBookings) {
                BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
                bookingResponseDTO.setBookingId(booking.getBookingId());
                bookingResponseDTO.setUser(booking.getUser().getUserId());
                bookingResponseDTO.setField(booking.getField().getFieldId());
                bookingResponseDTO.setBookingCode(booking.getBookingCode());
                bookingResponseDTO.setStartTime(booking.getStartTime());
                bookingResponseDTO.setEndTime(booking.getEndTime());
                bookingResponseDTO.setTotalPrice(booking.getTotalPrice());
                listBookingDTO.add(bookingResponseDTO);
            }
        } catch (Exception e) {
            System.err.println("Error fetching all bookings: " + e.getMessage());
        }

        return listBookingDTO;
    }

    @Override
    public BookingResponseDTO getBookingByCode(String code) {
        try {
            Booking booking = bookingRepo.findByBookingCode(code);
            if (booking != null) {
                return new BookingResponseDTO(
                        booking.getBookingId(),
                        booking.getBookingCode(),
                        booking.getUser().getUserId(),
                        booking.getField().getFieldId(),
                        booking.getBookingDate(),
                        booking.getStartTime(),
                        booking.getEndTime(),
                        booking.getStatus(),
                        booking.getTotalPrice(),
                        booking.getInvoice().getInvoiceId()
                );
            }
        } catch (Exception e) {
            System.err.println("Error fetching booking by code: " + e.getMessage());
        }
        return null;
    }

    @Override
    public BookingResponseDTO createNewBooking(BookingResponseDTO booking) {
        try {
            Invoice invoiceEntity = invoiceRepo.findById(booking.getInvoice()).get();
            Booking updatedBooking = new Booking();
            updatedBooking.setBookingDate(booking.getBookingDate());
            updatedBooking.setBookingCode(booking.getBookingCode());
            updatedBooking.setInvoice(invoiceEntity);
            updatedBooking.setEndTime(booking.getEndTime());
            updatedBooking.setStartTime(booking.getStartTime());
            updatedBooking.setTotalPrice(booking.getTotalPrice());
            updatedBooking.setStatus(booking.getStatus());
            Field field = new Field();
            field.setFieldId(booking.getField());
            updatedBooking.setField(field);
            User u = new User();
            u.setUserId(booking.getUser());
            updatedBooking.setUser(u);

            bookingRepo.save(updatedBooking);
            return new BookingResponseDTO(
                    updatedBooking.getBookingId(),
                    updatedBooking.getBookingCode(),
                    updatedBooking.getUser().getUserId(),
                    updatedBooking.getField().getFieldId(),
                    updatedBooking.getBookingDate(),
                    updatedBooking.getStartTime(),
                    updatedBooking.getEndTime(),
                    updatedBooking.getStatus(),
                    updatedBooking.getTotalPrice(),
                    updatedBooking.getInvoice().getInvoiceId()
            );
        } catch (Exception e) {
            System.err.println("Error creating booking: " + e.getMessage());
            return null;
        }
    }

    @Override
    public BookingResponseDTO updateBooking(int id, Booking bookingDetails) {
        try {
            Booking existingBooking = bookingRepo.findById(id).orElse(null);
            if (existingBooking != null) {
                existingBooking.setUser(bookingDetails.getUser());
                existingBooking.setField(bookingDetails.getField());
                existingBooking.setBookingCode(bookingDetails.getBookingCode());
                existingBooking.setStartTime(bookingDetails.getStartTime());
                existingBooking.setEndTime(bookingDetails.getEndTime());

                Booking updatedBooking = bookingRepo.save(existingBooking);
                return new BookingResponseDTO(
                        updatedBooking.getBookingId(),
                        updatedBooking.getBookingCode(),
                        updatedBooking.getUser().getUserId(),
                        updatedBooking.getField().getFieldId(),
                        updatedBooking.getBookingDate(),
                        updatedBooking.getStartTime(),
                        updatedBooking.getEndTime(),
                        updatedBooking.getStatus(),
                        updatedBooking.getTotalPrice(),
                        updatedBooking.getInvoice().getInvoiceId()
                );
            }
        } catch (Exception e) {
            System.err.println("Error updating booking: " + e.getMessage());
        }
        return null;
    }

    }
