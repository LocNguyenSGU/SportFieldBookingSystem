package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.CouponDTO.CouponResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Coupon;

import java.util.List;

public interface CouponServiceImpl{
    public List<CouponResponseDTO> getAllCoupons();
    public CouponResponseDTO getCouponById(int id);
    CouponResponseDTO createCoupon(Coupon coupon);
    boolean deleteCoupon(int id);
    CouponResponseDTO updateCoupon(int id, CouponResponseDTO couponDetails);
    public boolean isCouponExists(String code);
}
