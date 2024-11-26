package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.Exception.ResourceNotFoundException;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldListDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.*;
import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;
import com.example.SportFieldBookingSystem.Mapper.FieldMapper;
import com.example.SportFieldBookingSystem.Repository.*;
import com.example.SportFieldBookingSystem.Service.FieldService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FieldTypeRepository fieldTypeRepository;
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;

    @Autowired
    public FieldServiceImpl(FieldRepository fieldRepository, FieldTypeRepository fieldTypeRepository,
                            LocationRepository locationRepository,
                            ModelMapper modelMapper, TimeSlotRepository timeSlotRepository,
                            UserRepository userRepository) {
        this.fieldRepository = fieldRepository;
        this.fieldTypeRepository = fieldTypeRepository;
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
        this.timeSlotRepository = timeSlotRepository;
        this.userRepository = userRepository;
    }

    public String generateFieldCode() {
        // Generate a UUID and take only the alphanumeric characters
        String randomStr = UUID.randomUUID().toString().replace("-", "").substring(0, 5);

        // Return the field code with "F-" followed by the random string
        return "F-" + randomStr;
    }

    @Override
    @Transactional
    public FieldGetDTO createField(FieldCreateDTO fieldCreateDTO) {
        // Map the basic fields from DTO to Field entity
        Field field = new Field();
        field.setFieldCode(generateFieldCode());
        field.setFieldName(fieldCreateDTO.getFieldName());
        field.setFieldAddress(fieldCreateDTO.getAddress());
        field.setCapacity(fieldCreateDTO.getCapacity());
        field.setPricePerHour(fieldCreateDTO.getPricePerHour());

        FieldType fieldType = fieldTypeRepository.findById(fieldCreateDTO.getFieldTypeId()).get();
        field.setFieldType(fieldType );


        // User (assuming you get userId from the session or security context)
        User user = userRepository.findById(fieldCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        field.setUser(user);

        // Status (Enum)
        field.setStatus(fieldCreateDTO.getStatus());

        // Handle Field Images (creating FieldImage entities and linking to Field)
        if (fieldCreateDTO.getFieldImageList() != null) {
            List<FieldImage> fieldImages = fieldCreateDTO.getFieldImageList().stream()
                    .map(imageUrl -> {
                        FieldImage fieldImage = new FieldImage();
                        fieldImage.setImageUrl(String.valueOf(imageUrl));
                        fieldImage.setField(field);  // Set the reference to the field
                        return fieldImage;
                    })
                    .collect(Collectors.toList());
            field.setFieldImageList(fieldImages);
        }

        // Handle Field Time Rules (creating FieldTimeRule entities)
//        if (fieldCreateDTO.getFieldTimeRuleList() != null) {
//            List<FieldTimeRule> fieldTimeRules = fieldCreateDTO.getFieldTimeRuleList().stream()
//                    .map(timeRule -> {
//                        FieldTimeRule fieldTimeRule = new FieldTimeRule();
//                        // Map the properties from FieldTimeRuleDTO to FieldTimeRule
//                        fieldTimeRule.setStartDate(timeRule.getStartDate());
//                        fieldTimeRule.setEndDate(timeRule.getEndDate());
//                        fieldTimeRule.setDaysOfWeek(timeRule.getDaysOfWeek());
//                        fieldTimeRule.setStartTime(timeRule.getStartTime());
//                        fieldTimeRule.setEndTime(timeRule.getEndTime());
//                        fieldTimeRule.setField(field); // Set the reference to the field
//                        return fieldTimeRule;
//                    })
//                    .collect(Collectors.toList());
//            field.setFieldTimeRuleList(fieldTimeRules);
//        }

        // Save the Field entity
        Field fieldSaved = fieldRepository.save(field);
//        // Generate TimeSlots for each FieldTimeRule
//        field.getFieldTimeRuleList().forEach(this::generateTimeSlotsFromRule);
        // Return the DTO
        return modelMapper.map(fieldSaved, FieldGetDTO.class);
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
                timeSlots.add(timeSlot);
            }
        }

        // Lưu toàn bộ TimeSlot
        timeSlotRepository.saveAll(timeSlots);
    }


    // Lấy Field theo ID
    @Override
    public FieldGetDTO getFieldById(int fieldId) {
        Field field = fieldRepository.findById(fieldId).orElseThrow(() -> new ResourceNotFoundException("Field not found with id: " + fieldId));
//        Hibernate.initialize(field.getTimeSlotList());
        return modelMapper.map(field, FieldGetDTO.class);
    }

//     Lấy tất cả Fields
    @Override
    public List<FieldListDTO> getAllFields() {
        return fieldRepository.findAll().stream()
                .map(field -> modelMapper.map(field, FieldListDTO.class))
                .collect(Collectors.toList());
    }

    // Cập nhật Field
    @Override
    public FieldGetDTO updateField(int fieldId, FieldUpdateDTO fieldUpdateDTO) {
        // Tìm Field hiện tại
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with id: " + fieldId));
        field.setFieldName(fieldUpdateDTO.getFieldName());
        field.setCapacity(fieldUpdateDTO.getCapacity());
        field.setPricePerHour(fieldUpdateDTO.getPricePerHour());
        FieldType newFieldType = fieldTypeRepository.findById(fieldUpdateDTO.getFieldTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("FieldType not found with id: " + fieldUpdateDTO.getFieldTypeId()));
        field.setFieldType(newFieldType);
        field.setStatus(FieldEnum.valueOf(fieldUpdateDTO.getStatus()));
        field.setFieldAddress(fieldUpdateDTO.getAddress());

        // Cập nhật FieldImage
        if (fieldUpdateDTO.getFieldImageList() != null) {
            Field finalField = field;
            List<FieldImage> images = fieldUpdateDTO.getFieldImageList().stream()
                    .map(imageDTO -> {
                        FieldImage fieldImage = new FieldImage();
                        fieldImage.setImageUrl(imageDTO.getImageUrl());
                        fieldImage.setField(finalField);
                        return fieldImage;
                    })
                    .collect(Collectors.toList());
            field.setFieldImageList(images);
        }

        // Lưu Field
        field = fieldRepository.save(field);

        // Trả về DTO
        return modelMapper.map(field, FieldGetDTO.class);
    }



    // Xóa Field
    @Override
    public boolean deleteField(int fieldId) {
        if (fieldRepository.existsById(fieldId)) {
            fieldRepository.deleteById(fieldId);
            return true;
        }
        return false;
    }

    @Override
    public List<FieldListDTO> getFieldsByTimKiem(int loai, String ten, String diaChi) {
        List<Field> fields = fieldRepository.findFieldsByCriteria(loai, ten, diaChi);

        return fields.stream()
                .map(field -> FieldMapper.mapToFieldListDTO(field)) // Use your custom FieldMapper
                .collect(Collectors.toList());
    }

}
