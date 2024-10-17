package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
}
