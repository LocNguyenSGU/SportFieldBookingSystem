package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "field_image")
public class FieldImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageId;

    @ManyToOne
    @JoinColumn(name = "field_id" , nullable = false)
    private Field field;

    @Column(name = "image_url", length = 255, nullable = false)
    private String imageUrl;

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum activeEnum = ActiveEnum.ACTIVE;
}
