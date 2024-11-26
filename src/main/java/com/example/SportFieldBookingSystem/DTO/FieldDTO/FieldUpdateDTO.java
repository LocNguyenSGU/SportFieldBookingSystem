package com.example.SportFieldBookingSystem.DTO.FieldDTO;

import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageCreateDTO;
import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FieldUpdateDTO {
    private String fieldName;
    private int capacity;
    private double pricePerHour;
    private int fieldTypeId;
    private String status;
    private String address;
    private String fieldAddress;
    private String longitude;
    private String latitude;
    private ArrayList<String> fieldImageList;
}
