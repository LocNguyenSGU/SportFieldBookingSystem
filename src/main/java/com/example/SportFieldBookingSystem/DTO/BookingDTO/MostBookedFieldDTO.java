package com.example.SportFieldBookingSystem.DTO.BookingDTO;

import lombok.Data;

@Data
public class MostBookedFieldDTO {
    private int fieldId;         // ID sân
    private String fieldName;    // Tên sân
    private long bookingCount;   // Số lần đặt

    // Constructor có tham số
    public MostBookedFieldDTO(int fieldId, String fieldName, long bookingCount) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.bookingCount = bookingCount;
    }
}
