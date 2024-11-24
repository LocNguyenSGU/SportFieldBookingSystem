package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceBookingRequestDTO;
import com.example.SportFieldBookingSystem.DTO.InvoiceDTO.InvoiceBookingResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Invoice;
import com.example.SportFieldBookingSystem.Service.Impl.BookingServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.InvoiceServiceImpl;
import com.example.SportFieldBookingSystem.Service.TimeSlotService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api/CustomerBooking")
public class CustomerBookingController {
    private final BookingServiceImpl bookingService;
    private final InvoiceServiceImpl invoiceServiceImpl;
    private final TimeSlotService timeSlotService;
    @Autowired
    public CustomerBookingController(BookingServiceImpl bookingService, InvoiceServiceImpl invoiceServiceImpl, TimeSlotService timeSlotService) {
        this.bookingService = bookingService;
        this.invoiceServiceImpl = invoiceServiceImpl;
        this.timeSlotService = timeSlotService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody InvoiceBookingRequestDTO invoiceRequestDTO) {
        try {
//            double totalAmount =  invoiceRequestDTO.getBooking().getSelectedEvents().stream()
//                    .mapToDouble(Event::getTotalPrice)
//                    .sum();
//            invoiceRequestDTO.setTotalAmount(totalAmount);
            InvoiceBookingResponseDTO invoice = invoiceServiceImpl.createInvoice(invoiceRequestDTO);
            return ResponseEntity.ok("Booking and Invoice created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while processing the request: " + e.getMessage());
        }
    }
}
