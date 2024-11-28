package com.example.SportFieldBookingSystem.Controller;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

        @GetMapping("/url")
        public ResponseData goToPayment(@RequestParam String amount, HttpServletRequest request) throws Exception {
    
            URL paymentUrl = paymentService.createPaymentUrl(Integer.parseInt(amount), "Thanh toan", request);
            ResponseData response = new ResponseData();
            response.setData(paymentUrl);
            response.setStatusCode(200);
            response.setMessage("url for payment website");
    
            return response;
        }

    @GetMapping("/vnpayReturn")
    public ResponseEntity<?> vnpayReturn(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> params.put(key, value[0]));

        ResponseData response = new ResponseData();

        String responseCode = params.get("vnp_ResponseCode");
        String vnp_OrderInfo = params.get("vnp_OrderInfo"); // Lấy giá trị từ tham số trả về


        try {
            // Kiểm tra kết quả thanh toán từ VNPay
            if ("00".equals(responseCode)) {
                System.out.println(vnp_OrderInfo);

                response.setMessage("Thanh toán thành công");
                response.setData(null);
                response.setStatusCode(200);

                String redirectUrl = "http://localhost:5173/payment-result?statusCode=" + response.getStatusCode() +
                        "&message=" + URLEncoder.encode(response.getMessage(), "UTF-8");
                return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
            } else {
                String redirectUrl = "http://localhost:5173/payment-result?statusCode=400";
                response.setData(redirectUrl);
                response.setMessage("Thanh toán thất bại: " + responseCode);
                response.setStatusCode(400); // Bad Request
                return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
            }

        } catch (Exception e) {
            response.setMessage("Có lỗi xảy ra: " + e.getMessage());
            response.setStatusCode(500); // Internal Server Error
        }

        String redirectUrl = "http://localhost:5173/payment-result?statusCode=400";
        response.setData(redirectUrl);
        response.setMessage("Thanh toán thất bại: " + responseCode);
        response.setStatusCode(400); // Bad Request
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
    }
}