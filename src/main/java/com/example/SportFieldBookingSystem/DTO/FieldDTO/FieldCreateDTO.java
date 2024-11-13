package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FieldCreateDTO {
    private String fieldCode;
    private String fieldName;
    private int capacity;
    private double pricePerHour;
    private int fieldTypeId;     // Chỉ lưu ID của FieldType để liên kết
    private int locationId;      // Chỉ lưu ID của Location để liên kết
    private int userId;          // ID của chủ sở hữu
    private String status;       // Trạng thái dưới dạng chuỗi
    private List<String> fieldImageUrls; // Danh sách URL ảnh của sân
    private List<Integer> facilityIds;   // Danh sách ID các tiện ích sân
}
