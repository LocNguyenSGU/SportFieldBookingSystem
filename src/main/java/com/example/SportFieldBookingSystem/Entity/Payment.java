package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import com.example.SportFieldBookingSystem.Enum.PaymentMethodEnum;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;
    @Column(name = "payment_code", length = 10, unique = true, updatable = false)
    private String paymentCode;
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "amount")
    private double amount;

    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(name = "payment_method", length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

}
