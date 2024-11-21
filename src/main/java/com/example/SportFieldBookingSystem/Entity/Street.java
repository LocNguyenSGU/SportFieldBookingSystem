package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "street")
public class Street { // duong pho
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "street_id")
    private int streetId;

    @Column(name = "street_name", length = 100, nullable = false)
    private String streetName;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

}
