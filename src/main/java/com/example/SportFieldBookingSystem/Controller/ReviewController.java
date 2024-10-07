package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.ReviewDTO.ReviewDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping // Đây là ánh xạ cho yêu cầu GET đến /review
    public ResponseEntity<?> getAllReviews(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách đánh giá
        Page<ReviewDTO> reviewResponseDTOList = reviewService.getAllReview(page, size);

        // Kiểm tra nếu danh sách đánh giá trống
        if (reviewResponseDTOList.isEmpty()) {
            responseData.setMessage("No reviews found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }

        // Nếu có dữ liệu
        responseData.setData(reviewResponseDTOList);
        responseData.setMessage("Successfully retrieved all reviews.");
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/field/{fieldId}")
    public ResponseEntity<?> getAllReviewsByFieldId(@PathVariable int fieldId) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách đánh giá
        List<ReviewDTO> reviewResponseDTOList = reviewService.getAllReviewByFieldId(fieldId);

        // Kiểm tra nếu danh sách đánh giá trống
        if (reviewResponseDTOList.isEmpty()) {
            responseData.setMessage("No reviews found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }

        // Nếu có dữ liệu
        responseData.setData(reviewResponseDTOList);
        responseData.setMessage("Successfully retrieved all reviews.");
        return ResponseEntity.ok(responseData);
    }
}