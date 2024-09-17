package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location { // dia diem
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int locationId;
    @Column(name = "location_number")
    private String locationNumber; // so nha; vd: 54/32/4D
    @ManyToOne
    @JoinColumn(name="street_id")
    private Street street;
}
