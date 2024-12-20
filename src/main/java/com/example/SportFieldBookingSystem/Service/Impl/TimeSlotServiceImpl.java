package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.Exception.ResourceNotFoundException;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldListDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotCreateDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;
import com.example.SportFieldBookingSystem.Repository.TimeSlotRepository;
import com.example.SportFieldBookingSystem.Service.TimeSlotService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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

    // Scheduled task chạy mỗi ngày vào lúc 1h sáng để xóa các TimeSlot đã hết hạn
    @Scheduled(cron = "0 0 0 * * ?") // Chạy mỗi ngày vào lúc 0 giờ sáng
    @Transactional
    public void deleteExpiredTimeSlots() {
        Date currentDate = new Date(); // Lấy thời gian hiện tại
        // Xóa tất cả các TimeSlot có ngày nhỏ hơn ngày hiện tại
        timeSlotRepository.deleteByDateBefore(currentDate);
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

    public TimeSlotResponseDTO setStatus(int id, TimeSlotEnum status) {
        try {
            TimeSlot timeSlot = timeSlotRepository.findById(id).get();
            timeSlot.setStatus(status);
            TimeSlot timeSlotSave = timeSlotRepository.save(timeSlot);
            return modelMapper.map(timeSlotSave, TimeSlotResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Time slot set status failed");
        }
    }

    public Page<TimeSlotResponseDTO> searchTimeSlots(Integer fieldId,
                                             String startDate,
                                             String endDate,
                                             TimeSlotEnum status,
                                             int page,
                                             int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Specification<TimeSlot> specification = Specification.where(fieldId != null ? byFieldId(fieldId) : null)
                .and(startDate != null ? byFromDate(startDate) : null)
                .and(endDate!= null ? byToDate(endDate) : null)
                .and(status != null ? byStatus(String.valueOf(status)) : null);

        Page<TimeSlot> timeSlotPage = timeSlotRepository.findAll(specification, pageable);

        // Convert entities to DTOs
        return timeSlotPage.map(timeSlot -> modelMapper.map(timeSlot, TimeSlotResponseDTO.class));
    }

    private Specification<TimeSlot> byFieldId(Integer fieldId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("field").get("fieldId"), fieldId);
    }

    private Specification<TimeSlot> byFromDate(String fromDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.parse(fromDate));
    }

    private Specification<TimeSlot> byToDate(String toDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("date"), LocalDate.parse(toDate));
    }

    private Specification<TimeSlot> byStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), TimeSlotEnum.valueOf(status));
    }
}
