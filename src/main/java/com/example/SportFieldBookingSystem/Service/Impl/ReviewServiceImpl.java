package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.ReviewDTO.ReviewDTO;
import com.example.SportFieldBookingSystem.Entity.Review;
import com.example.SportFieldBookingSystem.Mapper.ReviewMapper;
import com.example.SportFieldBookingSystem.Repository.ReviewRepository;
import com.example.SportFieldBookingSystem.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    private ReviewMapper reviewMapper = new ReviewMapper();

    @Override
    public Page<ReviewDTO> getAllReview(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findAll(pageable); // Lấy tất cả đánh giá từ repository

        // Chuyển đổi từng Review sang ReviewDTO và trả về Page<ReviewDTO>
        return reviews.map(reviewMapper::toReviewDTO);
    }


    @Override
    public List<ReviewDTO> getAllReviewByFieldId(int fieldId) {
        List<Review> reviewList = reviewRepository.findReviewByField_FieldId(fieldId);
        if (reviewList.isEmpty()) {
            System.out.println("No reviews found for fieldId: " + fieldId);
        } else {
            System.out.println("Found " + reviewList.size() + " reviews for fieldId: " + fieldId);
        }
        for(Review review : reviewList) {
            System.out.println("id" + review.getReviewId());
        }

        List<ReviewDTO> reviewDTOList = reviewList.stream()
                .map(review -> {
                    try {
                        return reviewMapper.toReviewDTO(review);
                    } catch (Exception e) {
                        System.out.println("Error mapping review: " + e.getMessage());
                        return null;
                    }
                }).collect(Collectors.toList());

        return reviewDTOList;
    }
}
