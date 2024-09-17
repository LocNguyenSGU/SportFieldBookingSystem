package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "district")
public class District { // quan
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private int districtId;

    @Column(name = "district_name", length = 100, nullable = false)
    private String districtName;
    @OneToMany(mappedBy = "district")
    private List<Street> streetList;

}
