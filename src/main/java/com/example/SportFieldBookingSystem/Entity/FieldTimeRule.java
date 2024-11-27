package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "field_time_rule")
public class FieldTimeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "days_of_week", nullable = false, length = 60)
    private String daysOfWeek;

    @OneToMany(mappedBy = "fieldTimeRule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeSlot> timeSlots;
}
