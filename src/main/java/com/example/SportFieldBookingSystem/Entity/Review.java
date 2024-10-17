package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.FieldEnum;
import com.example.SportFieldBookingSystem.Enum.ReviewEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor // Thêm constructor không tham số
@AllArgsConstructor // Thêm constructor có tham số
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "time_create", nullable = true)
    private LocalDateTime timeCreate;

    @ManyToOne
    @JoinColumn(name="field_id")
    private Field field;

    @Column(name = "rating", nullable = false)
    private int rating;  // Đánh giá (ví dụ: từ 1 đến 5)

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private ReviewEnum status;

}
