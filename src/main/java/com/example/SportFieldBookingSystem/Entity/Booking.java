package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.BookingEnum;
import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking { // dat san
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "booking_code", unique = true, length = 10, updatable = false)
    private String bookingCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private BookingEnum status;

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name="invoice_id")
    private Invoice invoice;

    public Booking() {

    }

    public Booking(int bookingId, String bookingCode, User user, Field field, LocalDate bookingDate, LocalTime startTime, LocalTime endTime, BookingEnum status, double totalPrice, Invoice invoice) {
        this.bookingId = bookingId;
        this.bookingCode = bookingCode;
        this.user = user;
        this.field = field;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.totalPrice = totalPrice;
        this.invoice = invoice;
    }

}
