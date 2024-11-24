package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.BookingEnum;
import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Date;

@Data
@Entity
@Table(name = "time_slot")
public class TimeSlot { //TimeSlot sẽ lưu trữ thông tin về các khoảng thời gian trống của sân thể dục để người dùng có thể đặt sân.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeslot_id")
    private int timeslotId;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private TimeSlotEnum status;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
