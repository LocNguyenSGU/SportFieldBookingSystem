package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.Exception.ResourceNotFoundException;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldListDTO;
import com.example.SportFieldBookingSystem.DTO.FieldFacilityDTO.FieldFacilityResponseDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotCreateDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import com.example.SportFieldBookingSystem.Repository.TimeSlotRepository;
import com.example.SportFieldBookingSystem.Service.TimeSlotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository, ModelMapper modelMapper) {
        this.timeSlotRepository = timeSlotRepository;
        this.modelMapper = modelMapper;
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
        return timeSlotRepository.findAll().stream()
                .map(timeSlot -> modelMapper.map(timeSlot, TimeSlotResponseDTO.class))
                .collect(Collectors.toList());
    }

    public TimeSlotResponseDTO getTimeSlot(int timeslotId) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeslotId).orElseThrow(() -> new ResourceNotFoundException("Not found time slot with id: " + timeslotId));
        return modelMapper.map(timeSlot, TimeSlotResponseDTO.class);
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
