package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "invalidtoken")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidToken {
    @Id
    @Column(name = "id_token")
    private String idToken;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate; // Thời gian hết hạn của token
}