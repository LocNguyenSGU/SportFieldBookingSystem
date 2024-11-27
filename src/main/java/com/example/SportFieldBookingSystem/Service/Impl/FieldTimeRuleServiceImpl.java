package com.example.SportFieldBookingSystem.Service.Impl;


import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleDTO;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Entity.FieldTimeRule;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;
import com.example.SportFieldBookingSystem.Mapper.FieldMapper;
import com.example.SportFieldBookingSystem.Repository.FieldRepository;
import com.example.SportFieldBookingSystem.Repository.FieldTimeRuleRepository;
import com.example.SportFieldBookingSystem.Repository.TimeSlotRepository;
import com.example.SportFieldBookingSystem.Service.FieldTimeRuleService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FieldTimeRuleServiceImpl implements FieldTimeRuleService {
    private final FieldTimeRuleRepository fieldTimeRuleRepository;
    private final FieldRepository fieldRepository;
    private final TimeSlotRepository timeSlotRepository;
    private ModelMapper modelMapper;

    @Autowired
    FieldTimeRuleServiceImpl(FieldTimeRuleRepository fieldTimeRuleRepository, TimeSlotRepository timeSlotRepository,
                             ModelMapper modelMapper, FieldRepository fieldRepository) {
        this.fieldTimeRuleRepository = fieldTimeRuleRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.modelMapper = modelMapper;
        this.fieldRepository = fieldRepository;
    }


    @Override
    public boolean isTimeSlotBooked(int id) {
        Optional<FieldTimeRule> fieldTimeRuleList = fieldTimeRuleRepository.findById(id);
        if (fieldTimeRuleList.isPresent()) {
            for (TimeSlot timeSlot : fieldTimeRuleList.get().getTimeSlots()) {
                if(timeSlot.getStatus() != TimeSlotEnum.AVAILABLE) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public List<FieldTimeRuleDTO> getAllFieldTimeRule() {
        List<FieldTimeRule> fieldTimeRules = fieldTimeRuleRepository.findAll();

        return fieldTimeRules.stream()
                .map(fieldTimeRule -> modelMapper.map(fieldTimeRule, FieldTimeRuleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FieldTimeRuleDTO> getFieldTimeRuleByFieldId(int fieldId, int page, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page - 1, pageSize );
            Page<FieldTimeRule> fieldTimeRuleList = fieldTimeRuleRepository.findAllByFieldFieldId(fieldId, pageable);
            return fieldTimeRuleList.map(fieldTimeRule -> modelMapper.map(fieldTimeRule, FieldTimeRuleDTO.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FieldTimeRuleDTO updateFieldTimeRule(int id,FieldTimeRuleDTO fieldTimeRuleDTO) {
        FieldTimeRule fieldTimeRule = fieldTimeRuleRepository.findById(id).get();
        modelMapper.map(fieldTimeRuleDTO, fieldTimeRule);
        fieldTimeRule = fieldTimeRuleRepository.save(fieldTimeRule);
        return modelMapper.map(fieldTimeRule, FieldTimeRuleDTO.class);
    }

    @Override
    public FieldTimeRuleDTO getFieldTimeRuleById(int id) {
        FieldTimeRule fieldTimeRule = fieldTimeRuleRepository.findById(id).orElse(null);
        return modelMapper.map(fieldTimeRule, FieldTimeRuleDTO.class);
    }

    public boolean deleteFieldTimeRule(int id) {
        if(fieldTimeRuleRepository.existsById(id)) {
            fieldTimeRuleRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Transactional
    public FieldTimeRuleDTO createFieldTimeRule(FieldTimeRuleCreateDTO rule) {
        try {
            if (isTimeSlotConflict(rule)) {
                throw new IllegalArgumentException("Time slot conflict detected for the specified field.");
            }
            // Lưu FieldTimeRule
            FieldTimeRule entity = new FieldTimeRule();
            entity.setStartDate(rule.getStartDate());
            entity.setEndDate(rule.getEndDate());
            entity.setStartTime(rule.getStartTime());
            entity.setEndTime(rule.getEndTime());
            Field field = fieldRepository.findById(rule.getFieldId()).get();
            entity.setField(field);
            entity.setDaysOfWeek(rule.getDaysOfWeek());

            FieldTimeRule savedRule = fieldTimeRuleRepository.save(entity);

            // Tạo TimeSlot từ FieldTimeRule
            generateTimeSlotsFromRule(savedRule);

            return modelMapper.map(savedRule, FieldTimeRuleDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi: "+ e.getMessage());
        }

    }

    public boolean isTimeSlotConflict(FieldTimeRuleCreateDTO dto) {
        // Kiểm tra xem các TimeSlot có trùng khung giờ không
        return timeSlotRepository.existsByFieldIdAndDateBetweenAndTimeOverlap(
                dto.getFieldId(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getStartTime(),
                dto.getEndTime()
        );
    }


    private void generateTimeSlotsFromRule(FieldTimeRule rule) {
        List<DayOfWeek> daysOfWeek = Arrays.stream(rule.getDaysOfWeek().split(","))
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());

        // Tạo danh sách TimeSlot
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (LocalDate date = rule.getStartDate(); !date.isAfter(rule.getEndDate()); date = date.plusDays(1)) {
            if (daysOfWeek.contains(date.getDayOfWeek())) {
                // Chia nhỏ thời gian thành các TimeSlot
                LocalTime startTime = rule.getStartTime();
                LocalTime endTime = rule.getEndTime();
                    // Tạo TimeSlot
                    TimeSlot timeSlot = new TimeSlot();
                    timeSlot.setField(rule.getField());
                    timeSlot.setDate(Date.valueOf(date)); // Chuyển đổi LocalDate -> Date
                    timeSlot.setStartTime(Time.valueOf(startTime));
                    timeSlot.setEndTime(Time.valueOf(endTime));
                    timeSlot.setStatus(TimeSlotEnum.AVAILABLE); // Trạng thái mặc định
                    timeSlot.setFieldTimeRule(rule);
                    timeSlots.add(timeSlot);
            }
        }

        // Lưu toàn bộ TimeSlot
        timeSlotRepository.saveAll(timeSlots);
    }

    @Transactional
    public void updateTimeSlotsForRule(FieldTimeRuleDTO updatedRule) {
        // Lấy các TimeSlot hiện tại liên quan đến Field và các ngày của FieldTimeRule cũ
        List<TimeSlot> existingTimeSlots = timeSlotRepository.findByFieldAndDateBetween(
                updatedRule.getField(),
                Date.valueOf(updatedRule.getStartDate()),
                Date.valueOf(updatedRule.getEndDate())
        );

        // Tạo danh sách TimeSlot mới từ FieldTimeRule đã chỉnh sửa
        List<DayOfWeek> updatedDaysOfWeek = Arrays.stream(updatedRule.getDaysOfWeek().split(","))
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());

        List<TimeSlot> newTimeSlots = new ArrayList<>();
        for (LocalDate date = updatedRule.getStartDate(); !date.isAfter(updatedRule.getEndDate()); date = date.plusDays(1)) {
            if (updatedDaysOfWeek.contains(date.getDayOfWeek())) {
                LocalTime startTime = updatedRule.getStartTime();
                while (!startTime.isAfter(updatedRule.getEndTime())) {
                    LocalTime endTime = startTime.plusMinutes(30); // Mỗi slot dài 30 phút
                    if (endTime.isAfter(updatedRule.getEndTime())) {
                        break;
                    }

                    // Tạo TimeSlot mới
                    TimeSlot timeSlot = new TimeSlot();
                    timeSlot.setField(modelMapper.map(updatedRule.getField(), Field.class));
                    timeSlot.setDate(Date.valueOf(date)); // Chuyển đổi LocalDate -> Date
                    timeSlot.setStartTime(Time.valueOf(startTime));
                    timeSlot.setEndTime(Time.valueOf(endTime));
                    timeSlot.setStatus(TimeSlotEnum.AVAILABLE); // Trạng thái mặc định
                    newTimeSlots.add(timeSlot);

                    startTime = endTime; // Tiến tới slot tiếp theo
                }
            }
        }

        // Xóa các TimeSlot không còn phù hợp (những TimeSlot đã bị xóa hoặc thay đổi)
        for (TimeSlot existingSlot : existingTimeSlots) {
            boolean slotExistsInNew = newTimeSlots.stream().anyMatch(newSlot ->
                    newSlot.getDate().equals(existingSlot.getDate()) &&
                            newSlot.getStartTime().equals(existingSlot.getStartTime()) &&
                            newSlot.getEndTime().equals(existingSlot.getEndTime())
            );
            if (!slotExistsInNew) {
                timeSlotRepository.delete(existingSlot); // Xóa các TimeSlot không còn trong quy tắc mới
            }
        }

        // Thêm các TimeSlot mới vào cơ sở dữ liệu
        for (TimeSlot newSlot : newTimeSlots) {
            boolean slotExistsInExisting = existingTimeSlots.stream().anyMatch(existingSlot ->
                    existingSlot.getDate().equals(newSlot.getDate()) &&
                            existingSlot.getStartTime().equals(newSlot.getStartTime()) &&
                            existingSlot.getEndTime().equals(newSlot.getEndTime())
            );
            if (!slotExistsInExisting) {
                timeSlotRepository.save(newSlot); // Thêm các TimeSlot mới
            }
        }
    }


}
