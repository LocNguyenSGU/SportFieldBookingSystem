package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityResponseDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotCreateDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import com.example.SportFieldBookingSystem.Repository.TimeSlotRepository;
import com.example.SportFieldBookingSystem.Service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    // create
    public TimeSlotResponseDTO createTimeSlot(TimeSlotCreateDTO timeSlotCreateDTO) {
        try {
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setDate(timeSlotCreateDTO.getDate());
            timeSlot.setStartTime(timeSlotCreateDTO.getStartTime());
            timeSlot.setEndTime(timeSlotCreateDTO.getEndTime());

            TimeSlot timeSlotSave = timeSlotRepository.save(timeSlot);

            TimeSlotResponseDTO timeSlotResponseDTO = new TimeSlotResponseDTO();
            timeSlotResponseDTO.setTimeslotId(timeSlotSave.getTimeslotId());
            timeSlotResponseDTO.setStartTime(timeSlotSave.getStartTime());
            timeSlotResponseDTO.setEndTime(timeSlotSave.getEndTime());
            timeSlotResponseDTO.setDate(timeSlotSave.getDate());
            return timeSlotResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Time slot creation failed");
        }
    }

    public List<TimeSlotResponseDTO> getAllTimeSlots() {
        List<TimeSlotResponseDTO> timeSlotResponseDTOList = new ArrayList<>();
        try {
            List<TimeSlot> timeSlots = timeSlotRepository.findAll();
            for (TimeSlot timeSlot : timeSlots) {
                TimeSlotResponseDTO timeSlotResponseDTO = new TimeSlotResponseDTO();
                timeSlotResponseDTO.setTimeslotId(timeSlot.getTimeslotId());
                timeSlotResponseDTO.setStartTime(timeSlot.getStartTime());
                timeSlotResponseDTO.setEndTime(timeSlot.getEndTime());
                timeSlotResponseDTO.setDate(timeSlot.getDate());
                timeSlotResponseDTOList.add(timeSlotResponseDTO);
            }
            return timeSlotResponseDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Time slot creation failed");
        }
    }

    public TimeSlotResponseDTO getTimeSlot(int timeslotId) {
        try {
            TimeSlot timeSlot = timeSlotRepository.findById(timeslotId).get();
            TimeSlotResponseDTO timeSlotResponseDTO = new TimeSlotResponseDTO();
            timeSlotResponseDTO.setTimeslotId(timeSlot.getTimeslotId());
            timeSlotResponseDTO.setStartTime(timeSlot.getStartTime());
            timeSlotResponseDTO.setEndTime(timeSlot.getEndTime());
            timeSlotResponseDTO.setDate(timeSlot.getDate());
            return timeSlotResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Time slot creation failed");
        }
    }

    public TimeSlotResponseDTO updateTimeSlot(int id, TimeSlotUpdateDTO timeSlotUpdateDTO) {
        try {
            TimeSlot timeSlot = timeSlotRepository.findById(id).get();

            timeSlot.setDate(timeSlotUpdateDTO.getDate());
            timeSlot.setStartTime(timeSlotUpdateDTO.getStartTime());
            timeSlot.setEndTime(timeSlotUpdateDTO.getEndTime());

            TimeSlot timeSlotSave = timeSlotRepository.save(timeSlot);
            TimeSlotResponseDTO timeSlotResponseDTO = new TimeSlotResponseDTO();
            timeSlotResponseDTO.setTimeslotId(timeSlotSave.getTimeslotId());
            timeSlotResponseDTO.setStartTime(timeSlotSave.getStartTime());
            timeSlotResponseDTO.setEndTime(timeSlotSave.getEndTime());
            timeSlotResponseDTO.setDate(timeSlotSave.getDate());
            return timeSlotResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Time slot update failed");
        }
    }

    public void deleteTimeSlot(int timeslotId) {
        try {
            TimeSlot timeSlot = timeSlotRepository.findById(timeslotId).get();

            timeSlotRepository.delete(timeSlot);
        } catch (Exception e) {
            throw new RuntimeException("Time slot deletion failed");
        }
    }
}
