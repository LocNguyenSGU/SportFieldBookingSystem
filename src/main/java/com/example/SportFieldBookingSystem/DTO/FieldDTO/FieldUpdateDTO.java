package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FieldUpdateDTO {
    private String fieldName;
    private int capacity;
    private double pricePerHour;
    private int fieldTypeId;
    private int locationId;
    private String status;
    private List<String> fieldImageUrls; // Danh sách URL mới hoặc đã cập nhật của ảnh sân
    private List<Integer> facilityIds;   // Danh sách ID các tiện ích cần cập nhật
}
