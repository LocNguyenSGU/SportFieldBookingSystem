package com.example.SportFieldBookingSystem.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String toEmail; // gui cho email nao
    private String subject; // tieu de la gi
    private String messageBody; // noi dung
    private DataSource attachment; // neu co file gui kem
}