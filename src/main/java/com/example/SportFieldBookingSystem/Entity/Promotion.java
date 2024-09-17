package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "promotion")
public class Promotion { // khuyen mai
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private int promotionId;

    @Column(name = "promo_code", length = 50, unique = true, nullable = false)
    private String promoCode;

    @Column(name = "discount_percentage", nullable = false)
    private double discountPercentage;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
}
