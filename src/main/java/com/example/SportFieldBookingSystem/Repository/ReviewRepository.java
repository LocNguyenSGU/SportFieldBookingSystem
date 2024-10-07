package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findAll(Pageable pageable);
    List<Review> findReviewByField_FieldId(int fieldId);
}
