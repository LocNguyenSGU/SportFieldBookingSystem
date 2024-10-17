package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;

import java.util.List;

public class FieldCreateDTO {

    private String fieldName;  // Tên sân
    private int capacity;      // Sức chứa
    private double pricePerHour; // Giá thuê theo giờ
    private int fieldTypeId;   // ID của loại sân
    private int locationId;    // ID của vị trí (có thể null)
    private String status;     // Trạng thái của sân (AVAILABLE/UNAVAILABLE)
    private List<String> facilities; // Danh sách tiện ích (ví dụ: "Wifi", "Lighting")
    private List<String> images;     // URL của các hình ảnh
    private List<TimeSlotResponseDTO> timeSlots; // Các khung giờ của sân
}
