package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.Entity.FieldTimeRule;
import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FieldCreateDTO {
    private String fieldName;
    private int capacity;
    private double pricePerHour;
    private int fieldTypeId;     // Chỉ lưu ID của FieldType để liên kết
    private int userId;          // ID của chủ sở hữu
    private FieldEnum status;    // Trạng thái dưới dạng chuỗi
    private List<FieldImageCreateDTO> fieldImageList; // Danh sách URL ảnh của sân
    private String address;
    private String longitude;
    private String latitude;
//    private List<FieldTimeRuleDTO> fieldTimeRuleList;
}
