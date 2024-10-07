package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.ReviewDTO.ReviewDTO;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Entity.Review;
import com.example.SportFieldBookingSystem.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    // Chuyển từ Review entity sang ReviewDTO
    public ReviewDTO toReviewDTO(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setUserId(review.getUser().getUserId());  // Chuyển userId từ User entity
        reviewDTO.setTimeCreate(review.getTimeCreate());

        // Lấy fieldId từ Field entity, nếu Field không null
        reviewDTO.setFieldId(review.getField() != null ? review.getField().getFieldId() : null);

        reviewDTO.setRating(review.getRating());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setStatus(review.getStatus());

        return reviewDTO;
    }

    // Chuyển từ ReviewDTO sang Review entity
    public Review toReview(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            return null;
        }
        Review review = new Review();
        review.setReviewId(reviewDTO.getReviewId());
        User user = new User();
        user.setUserId(reviewDTO.getUserId());
        review.setUser(user);
        Field field = new Field();
        field.setFieldId(reviewDTO.getFieldId());
        review.setField(field);

        review.setTimeCreate(reviewDTO.getTimeCreate());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setStatus(reviewDTO.getStatus());
        return review;
    }
}