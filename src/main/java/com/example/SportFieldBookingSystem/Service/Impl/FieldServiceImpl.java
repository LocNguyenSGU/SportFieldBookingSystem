package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.Exception.ResourceNotFoundException;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldGetDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldListDTO;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldUpdateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageResponseDTO;
import com.example.SportFieldBookingSystem.Entity.*;
import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import com.example.SportFieldBookingSystem.Enum.TimeSlotEnum;
import com.example.SportFieldBookingSystem.Mapper.FieldMapper;
import com.example.SportFieldBookingSystem.Repository.*;
import com.example.SportFieldBookingSystem.Service.FieldService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FieldServiceImpl implements FieldService {
    @Autowired
    private final FieldRepository fieldRepository;
    private final FieldTypeRepository fieldTypeRepository;
    private final ModelMapper modelMapper;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    @Autowired
    private FieldImageRepository fieldImageRepository;

    @Autowired
    public FieldServiceImpl(FieldRepository fieldRepository, FieldTypeRepository fieldTypeRepository,
                            ModelMapper modelMapper, TimeSlotRepository timeSlotRepository,
                            UserRepository userRepository) {
        this.fieldRepository = fieldRepository;
        this.fieldTypeRepository = fieldTypeRepository;
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
    public long countFieldsByStatus(FieldEnum status) {
        return fieldRepository.countFieldsByStatus(status);
    }

    @Override
    @Transactional
    public FieldGetDTO createField(FieldCreateDTO fieldCreateDTO) {
        // Map the basic fields from DTO to Field entity
        System.out.println("Starting to create field...");

        Field field = new Field();

        // Generate field code
        String fieldCode = generateFieldCode();
        System.out.println("Generated field code: " + fieldCode);
        field.setFieldCode(fieldCode);

        // Set field name
        field.setFieldName(fieldCreateDTO.getFieldName());
        System.out.println("Field name: " + fieldCreateDTO.getFieldName());

        // Set field address
        field.setFieldAddress(fieldCreateDTO.getFieldAddress());
        System.out.println("Field address: " + fieldCreateDTO.getFieldAddress());

        field.setLongitude(fieldCreateDTO.getLongitude());
        System.out.println("Field long longtitude: " + fieldCreateDTO.getLongitude());
        field.setLatitude(fieldCreateDTO.getLatitude());

        // Set capacity
        field.setCapacity(fieldCreateDTO.getCapacity());
        System.out.println("Field capacity: " + fieldCreateDTO.getCapacity());

        // Set price per hour
        field.setPricePerHour(fieldCreateDTO.getPricePerHour());
        System.out.println("Price per hour: " + fieldCreateDTO.getPricePerHour());

        // Fetch field type by ID
        FieldType fieldType = fieldTypeRepository.findById(fieldCreateDTO.getFieldTypeId())
                .orElseThrow(() -> new RuntimeException("Field type not found"));
        field.setFieldType(fieldType);
        System.out.println("Field type: " + fieldType.getFieldTypeName());

        // Fetch user by ID
        User user = userRepository.findById(fieldCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        field.setUser(user);
        System.out.println("User ID: " + user.getUserId());

        // Set status
        field.setStatus(fieldCreateDTO.getStatus());
        System.out.println("Field status: " + fieldCreateDTO.getStatus());

//        // Handle Field Images (creating FieldImage entities and linking to Field)
//        if (fieldCreateDTO.getFieldImageList() != null) {
//            List<FieldImage> fieldImages = fieldCreateDTO.getFieldImageList().stream()
//                    .map(imageUrl -> {
//                        FieldImage fieldImage = new FieldImage();
//                        fieldImage.setImageUrl(String.valueOf(imageUrl));
//                        fieldImage.setField(field);  // Set the reference to the field
//                        return fieldImage;
//                    })
//                    .collect(Collectors.toList());
//            field.setFieldImageList(fieldImages);
//        }

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
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with id: " + fieldId));

        // Lọc các hình ảnh có trạng thái ACTIVE
        List<FieldImageResponseDTO> activeFieldImageDTOs = field.getFieldImageList().stream()
                .filter(image -> image.getActiveEnum() == ActiveEnum.ACTIVE)
                .map(image -> modelMapper.map(image, FieldImageResponseDTO.class))
                .collect(Collectors.toList());

        // Chuyển đổi Field sang DTO
        FieldGetDTO fieldGetDTO = modelMapper.map(field, FieldGetDTO.class);
        fieldGetDTO.setFieldImageList(activeFieldImageDTOs);

        return fieldGetDTO;
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

        // Cập nhật các thuộc tính cho phép
        if (fieldUpdateDTO.getFieldName() != null) {
            field.setFieldName(fieldUpdateDTO.getFieldName());
        }

        if (fieldUpdateDTO.getCapacity() > 0) {
            field.setCapacity(fieldUpdateDTO.getCapacity());
        }

        if (fieldUpdateDTO.getPricePerHour() > 0) {
            field.setPricePerHour(fieldUpdateDTO.getPricePerHour());
        }
        if(fieldUpdateDTO.getFieldAddress() != null) {
            field.setFieldAddress(fieldUpdateDTO.getFieldAddress());
        }
        if(fieldUpdateDTO.getLatitude() != null) {
            field.setLatitude(fieldUpdateDTO.getLatitude());
        }
        if(fieldUpdateDTO.getLongitude() != null) {
            field.setLongitude(fieldUpdateDTO.getLongitude());
        }

        FieldType newFieldType = fieldTypeRepository.findById(fieldUpdateDTO.getFieldTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("FieldType not found with id: " + fieldUpdateDTO.getFieldTypeId()));
        field.setFieldType(newFieldType);

        if (fieldUpdateDTO.getStatus() != null) {
            field.setStatus(FieldEnum.valueOf(fieldUpdateDTO.getStatus()));
        }

        if (fieldUpdateDTO.getAddress() != null) {
            field.setFieldAddress(fieldUpdateDTO.getAddress());
        }
        deleteStringUrlNotExists(fieldId, fieldUpdateDTO.getFieldImageList());

        // Lưu Field
        field = fieldRepository.save(field);

        // Trả về DTO
        return modelMapper.map(field, FieldGetDTO.class);
    }
    @Transactional
    public void deleteStringUrlNotExists(int fieldId, ArrayList<String> listImageURLNew) {
        // Log danh sách URL mới
        System.out.println("listImageURLNew1: " + listImageURLNew);
        System.out.println("FieldID là: " + fieldId);

        // Lấy FieldDTO từ service
        FieldGetDTO field = getFieldById(fieldId);
        if (field == null) {
            throw new ResourceNotFoundException("Field not found with id: " + fieldId);
        }

        // Lấy danh sách URL cũ từ FieldDTO
        List<FieldImageResponseDTO> fieldImageResponseDTOS = field.getFieldImageList();
        if (fieldImageResponseDTOS == null || fieldImageResponseDTOS.isEmpty()) {
            System.out.println("No existing images found. Nothing to delete.");
            return;
        }

        ArrayList<String> urlCu = new ArrayList<>();
        for (FieldImageResponseDTO responseDTO : fieldImageResponseDTOS) {
            urlCu.add(responseDTO.getFieldImageURL());
        }
        System.out.println("listImageURlOld: " + urlCu);

        // Tìm các URL cần xóa (URL cũ không nằm trong danh sách mới)
        List<String> urlsToDelete = urlCu.stream()
                .filter(url -> !listImageURLNew.contains(url))
                .collect(Collectors.toList());
        System.out.println("urlsToDelete: " + urlsToDelete);

        for (String url : urlsToDelete) {
            Optional<FieldImage> fieldImageToRemove = fieldImageRepository.findFieldImageNative(url, fieldId);
            if (fieldImageToRemove.isPresent()) {
                FieldImage fieldImage = fieldImageToRemove.get();
                fieldImage.setActiveEnum(ActiveEnum.IN_ACTIVE); // Cập nhật trạng thái
                fieldImageRepository.save(fieldImage); // Lưu đối tượng đã sửa
                System.out.println("Set FieldImage to INACTIVE: " + fieldImage.getImageId());
            } else {
                System.out.println("FieldImage not found for URL: " + url + " with FieldID: " + fieldId);
            }
        }

        // Log trạng thái sau khi xử lý
        System.out.println("Completed deleting non-existing URLs.");
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
    @Override
    public Page<FieldGetDTO> searchFields(String fieldName, Integer fieldTypeId, Integer minCapacity, Integer maxCapacity, String fieldAddress, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Tìm các field theo tiêu chí tìm kiếm
        Page<Field> fieldPage = fieldRepository.findFields(
                fieldName,
                fieldTypeId != null ? fieldTypeId : null,
                minCapacity,
                maxCapacity,
                fieldAddress,
                pageable
        );

        // Chuyển đổi từ Field sang FieldGetDTO
        return fieldPage.map(field -> modelMapper.map(field, FieldGetDTO.class));
    }

    @Override
    public long countTotalFields() {
        return fieldRepository.countTotalFields();
    }


}
