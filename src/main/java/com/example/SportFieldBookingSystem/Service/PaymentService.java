package com.example.SportFieldBookingSystem.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public interface PaymentService {
    public URL createPaymentUrl(int amount, String orderInfo, HttpServletRequest request)throws Exception;
}
