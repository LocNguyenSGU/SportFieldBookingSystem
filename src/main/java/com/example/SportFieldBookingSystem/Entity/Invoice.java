package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.BookingEnum;
import com.example.SportFieldBookingSystem.Enum.InvoiceEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "invoice")
public class Invoice { // hoa don
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "invoice_code", length = 10, unique = true, updatable = false)
    private String invoiceCode;

    @OneToMany(mappedBy = "invoice")
    private List<Booking> bookingList;

    @Column(name = "inv_date")
    private Date invDate;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private InvoiceEnum status; // (Pending, Paid, Cancelled)
}
