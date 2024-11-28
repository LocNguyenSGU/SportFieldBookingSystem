package com.example.SportFieldBookingSystem.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private int statusCode;
    private String message;
    private Object data;
}
