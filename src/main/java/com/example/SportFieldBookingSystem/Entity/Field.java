package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import com.example.SportFieldBookingSystem.Enum.UserEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "field")
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
    @JoinColumn(name = "fieldType_id")
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
    private List<TimeSlot> timeSlotList;

    @OneToMany(mappedBy = "field")
    private List<FieldImage> fieldImageList;

    @OneToMany(mappedBy = "field")
    private List<Favorite> favoriteList;

    @OneToMany(mappedBy = "field")
    private List<FieldFacility> fieldFacilityList;

    @OneToMany(mappedBy = "field")
    private List<FieldMaintenance> fieldMaintenanceList ;

}
