package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageResponseDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTypeDTO.FieldTypeResponseDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FieldGetDTO {
    private int fieldId;
    private String fieldCode;
    private String fieldName;
    private int capacity;
    private double pricePerHour;
    private FieldTypeResponseDTO fieldType;     // Tên của FieldType (ví dụ: sân bóng, sân tennis)
    private String fieldAddress;    // Tên địa điểm
    private String longitude;
    private String latitude;
//    private String ownerName;     // Tên người sở hữu
    private String status;
    private List<FieldImageResponseDTO> fieldImageList; // URL của các ảnh
    private List<TimeSlotDTO> timeSlotList;
}
