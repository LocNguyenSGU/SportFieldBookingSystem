package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "field_facility")
public class FieldFacility { // tien ich cua san
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private int facilityId;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @Column(name = "facility_name", length = 255, nullable = false)
    private String facilityName;  // Tên của tiện ích
}
