package com.example.SportFieldBookingSystem.DTO.InvoiceDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TKTongQuatDTO {
    private int tongSoHoaDon;
    private double tongDoanhThu;
    private double doanhThuTrungBinh;
    private int tongSoBooking;
}
