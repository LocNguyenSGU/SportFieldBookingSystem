package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    // Tìm các TimeSlot theo BookingId
    TimeSlot findTimeSlotByBookingBookingId(Integer bookingId);
    List<TimeSlot> findByFieldAndDateBetween(FieldGetDTO fieldGetDTO, Date start, Date end);
    boolean deleteByDateBefore(Date date);

    @Query("SELECT CASE WHEN COUNT(ts) > 0 THEN TRUE ELSE FALSE END " +
            "FROM TimeSlot ts WHERE ts.field.fieldId = :fieldId " +
            "AND ts.date BETWEEN :startDate AND :endDate " +
            "AND ((ts.startTime < :endTime AND ts.endTime > :startTime))")
    boolean existsByFieldIdAndDateBetweenAndTimeOverlap(
            @Param("fieldId") int fieldId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
