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

    @Column(name = "field_address")
    private String fieldAddress;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

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

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<FieldTimeRule> fieldTimeRuleList;

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public FieldEnum getStatus() {
        return status;
    }

    public void setStatus(FieldEnum status) {
        this.status = status;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public List<TimeSlot> getTimeSlotList() {
        return timeSlotList;
    }

    public void setTimeSlotList(List<TimeSlot> timeSlotList) {
        this.timeSlotList = timeSlotList;
    }

    public List<FieldImage> getFieldImageList() {
        return fieldImageList;
    }

    public void setFieldImageList(List<FieldImage> fieldImageList) {
        this.fieldImageList = fieldImageList;
    }

    public List<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public List<FieldFacility> getFieldFacilityList() {
        return fieldFacilityList;
    }

    public void setFieldFacilityList(List<FieldFacility> fieldFacilityList) {
        this.fieldFacilityList = fieldFacilityList;
    }

    public List<FieldMaintenance> getFieldMaintenanceList() {
        return fieldMaintenanceList;
    }

    public void setFieldMaintenanceList(List<FieldMaintenance> fieldMaintenanceList) {
        this.fieldMaintenanceList = fieldMaintenanceList;
    }
}
