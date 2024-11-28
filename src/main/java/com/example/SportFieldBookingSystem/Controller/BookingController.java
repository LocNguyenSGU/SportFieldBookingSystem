package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.BookingDTO.BookingResponseDTO;
import com.example.SportFieldBookingSystem.DTO.BookingDTO.MostBookedFieldDTO;
import com.example.SportFieldBookingSystem.DTO.BookingDTO.QuarterlyRevenueDTO;
import com.example.SportFieldBookingSystem.DTO.BookingDTO.RevenueDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.Entity.Booking;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.Impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class BookingController {

    private final BookingServiceImpl bookingServiceImpl;
    private final ResponseData responseData = new ResponseData();

    @Autowired
    public BookingController(BookingServiceImpl bookingServiceImpl) {
        this.bookingServiceImpl = bookingServiceImpl;
    }

    // Lấy danh sách tất cả các Booking
    @GetMapping("/listBookings")
    public ResponseEntity<?> getAllBookings() {
        List<BookingResponseDTO> bookings = bookingServiceImpl.getAllBooking();
        if (bookings.isEmpty()) {
            responseData.setMessage("list bookings null");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setMessage("get all bookings success!");
        responseData.setData(bookings);
        return ResponseEntity.ok(responseData);
    }

    // Lấy Booking theo mã code
    @GetMapping("/booking/{code}")
    public ResponseEntity<?> getBookingByCode(@PathVariable String code) {
        BookingResponseDTO booking = bookingServiceImpl.getBookingByCode(code);
        if (booking == null) {
            responseData.setMessage("booking with code " + code + " not found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setMessage("get booking success!");
        responseData.setData(booking);
        return ResponseEntity.ok(responseData);
    }

    // Tạo mới Booking
    @PostMapping("/booking")
    public ResponseEntity<?> createBooking(@RequestBody BookingResponseDTO booking) {
        BookingResponseDTO createdBooking = bookingServiceImpl.createNewBooking(booking);
        if (createdBooking == null) {
            responseData.setMessage("create booking failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setMessage("create booking success!");
        responseData.setData(createdBooking);
        return ResponseEntity.ok(responseData);
    }



    // Cập nhật Booking
    @PutMapping("/booking/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable int id, @RequestBody Booking bookingDetails) {
        BookingResponseDTO updatedBooking = bookingServiceImpl.updateBooking(id, bookingDetails);

        if (updatedBooking == null) {
            responseData.setMessage("Booking with ID " + id + " not found or time conflict with existing bookings");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseData);
        }

        responseData.setMessage("Update booking success!");
        responseData.setData(updatedBooking);
        return ResponseEntity.ok(responseData);
    }

    // Lấy danh sách các sân đã được đặt
    @GetMapping("/bookedFields")
    public ResponseEntity<?> getBookedFields() {
        List<FieldGetDTO> bookedFields = bookingServiceImpl.getBookedFields();
        if (bookedFields.isEmpty()) {
            responseData.setMessage("No fields have been booked.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setMessage("List of booked fields fetched successfully!");
        responseData.setData(bookedFields);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/bookedFieldsWithTime")
    public ResponseEntity<?> getBookedFieldsWithTime() {
        List<BookingResponseDTO> bookedFieldsWithTime = bookingServiceImpl.getBookedFieldsWithTime();
        if (bookedFieldsWithTime.isEmpty()) {
            responseData.setMessage("No fields have been booked.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setMessage("List of booked fields with time fetched successfully!");
        responseData.setData(bookedFieldsWithTime);
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/revenueStatistics")
    public ResponseEntity<?> getRevenueStatistics() {
        List<RevenueDTO> revenueStatistics = bookingServiceImpl.getRevenueStatistics();
        if (revenueStatistics.isEmpty()) {
            responseData.setMessage("No revenue data available.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setMessage("Revenue statistics fetched successfully!");
        responseData.setData(revenueStatistics);
        return ResponseEntity.ok(responseData);
    }

    // Thống kê doanh thu theo quý
    @GetMapping("/revenueByQuarter")
    public ResponseEntity<?> getRevenueByQuarter() {
        List<QuarterlyRevenueDTO> revenueStatistics = bookingServiceImpl.getRevenueStatisticsByQuarter();
        if (revenueStatistics.isEmpty()) {
            responseData.setMessage("No revenue data available.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setMessage("Revenue statistics by quarter fetched successfully!");
        responseData.setData(revenueStatistics);
        return ResponseEntity.ok(responseData);
    }

    // Thống kê sân được đặt nhiều nhất
    @GetMapping("/mostBookedFields")
    public ResponseEntity<?> getMostBookedFields() {
        List<MostBookedFieldDTO> mostBookedFields = bookingServiceImpl.getMostBookedFields();
        if (mostBookedFields.isEmpty()) {
            responseData.setMessage("No booking data available.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }
        responseData.setMessage("Most booked fields fetched successfully!");
        responseData.setData(mostBookedFields);
        return ResponseEntity.ok(responseData);
    }

}
