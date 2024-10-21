package com.example.SportFieldBookingSystem.DTO.CouponDTO;

import com.example.SportFieldBookingSystem.Entity.User;

import java.util.Date;

public class CouponResponseDTO {
    private int couponId;
    private int user;
    private String code;
    private Date expirationDate;
    private double discountValue;

    public CouponResponseDTO() {

    }

    public CouponResponseDTO(int couponId, int user, String code, Date expirationDate, double discountValue) {
        this.couponId = couponId;
        this.user = user;
        this.code = code;
        this.expirationDate = expirationDate;
        this.discountValue = discountValue;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }
}
