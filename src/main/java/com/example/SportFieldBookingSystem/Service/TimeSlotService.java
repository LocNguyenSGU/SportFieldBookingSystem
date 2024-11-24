package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotCreateDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;

import java.util.List;

public interface TimeSlotService {
    TimeSlotResponseDTO createTimeSlot(TimeSlotCreateDTO timeSlotCreateDTO);

    TimeSlotResponseDTO updateTimeSlot(int id, TimeSlotUpdateDTO timeSlotUpdateDTO);

    List<TimeSlotResponseDTO> getAllTimeSlots();

    TimeSlotResponseDTO getTimeSlot(int id);

    void deleteTimeSlot(int id);

    TimeSlotResponseDTO setStatus(int id, TimeSlotEnum status);
}
