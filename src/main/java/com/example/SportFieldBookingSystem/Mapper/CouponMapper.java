package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.CouponDTO.CouponResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Coupon;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.CouponRespository;
import org.springframework.beans.factory.annotation.Autowired;

public class CouponMapper {
    public static CouponResponseDTO toDTO(Coupon coupon){
        CouponResponseDTO couponResponseDTO = new CouponResponseDTO();
        couponResponseDTO.setCouponId(coupon.getCouponId());
        couponResponseDTO.setUser(coupon.getUser().getUserId());
        couponResponseDTO.setCode(coupon.getCode());
        couponResponseDTO.setExpirationDate(coupon.getExpirationDate());
        couponResponseDTO.setDiscountValue(coupon.getDiscountValue());
        return couponResponseDTO;

    }
    public static Coupon toEntity(CouponResponseDTO couponDTO, User u){
        Coupon cp = new Coupon();
        cp.setCouponId(couponDTO.getCouponId());
        cp.setUser(u);
        cp.setCode(couponDTO.getCode());
        cp.setExpirationDate(couponDTO.getExpirationDate());
        cp.setDiscountValue(couponDTO.getDiscountValue());
        return cp;
    }
}
