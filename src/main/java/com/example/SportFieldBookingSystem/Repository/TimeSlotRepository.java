package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    // Tìm các TimeSlot theo BookingId
    TimeSlot findTimeSlotByBookingBookingId(Integer bookingId);
}
