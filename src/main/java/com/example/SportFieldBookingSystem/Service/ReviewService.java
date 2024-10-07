package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.ReviewDTO.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    Page<ReviewDTO> getAllReview(int page, int size);

    List<ReviewDTO> getAllReviewByFieldId(int fieldId);

}
