package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    // Tìm các TimeSlot theo BookingId
    TimeSlot findTimeSlotByBookingBookingId(Integer bookingId);
    List<TimeSlot> findByFieldAndDateBetween(FieldGetDTO fieldGetDTO, Date start, Date end);
    boolean deleteByDateBefore(Date date);
}
