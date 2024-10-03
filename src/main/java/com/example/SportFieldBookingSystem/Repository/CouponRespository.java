package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRespository extends JpaRepository<Coupon, Integer> {
    boolean existsByCode(String code);
}
