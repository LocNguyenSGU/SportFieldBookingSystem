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


@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public Page<ReviewDTO> getAllReview(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findAll(pageable); // Lấy tất cả đánh giá từ repository

        // Chuyển đổi từng Review sang ReviewDTO và trả về Page<ReviewDTO>
        return reviews.map(reviewMapper::toReviewDTO);
    }
}
