package com.example.SportFieldBookingSystem.DTO.ReviewDTO;

import com.example.SportFieldBookingSystem.Enum.ReviewEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor // Thêm constructor không tham số
@AllArgsConstructor // Thêm constructor có tham số
public class ReviewDTO {
    private int reviewId; // ID của đánh giá

    private int userId; // ID của người dùng (nếu cần)

    private LocalDateTime timeCreate; // Thời gian tạo đánh giá

    private int fieldId; // ID của sân (nếu cần)

    private int rating; // Đánh giá (từ 1 đến 5)

    private String comment; // Lời bình

    private ReviewEnum status; // Trạng thái đánh giá
}
