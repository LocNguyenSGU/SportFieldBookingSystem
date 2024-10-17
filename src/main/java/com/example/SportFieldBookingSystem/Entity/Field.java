package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import com.example.SportFieldBookingSystem.Enum.UserEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "field")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field { // san
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id")
    private int fieldId;

    @Column(name = "field_code", unique = true, length = 10, updatable = false)
    private String fieldCode;

    @Column(name = "field_name", nullable = false, length = 50)
    private String fieldName;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "price_per_hour")
    private double pricePerHour;

    @ManyToOne
    @JoinColumn(name = "fieldType_id", nullable = false)
    private FieldType fieldType;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private FieldEnum status;
    @OneToMany(mappedBy = "field")
    private List<Booking> bookingList;
    @OneToMany(mappedBy = "field")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "field")
    private List<TimeSlot> timeSlotList;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<FieldImage> fieldImageList;

    @OneToMany(mappedBy = "field")
    private List<Favorite> favoriteList;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<FieldFacility> fieldFacilityList;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<FieldMaintenance> fieldMaintenanceList ;

}
