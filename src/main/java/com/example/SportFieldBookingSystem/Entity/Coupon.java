package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon")
public class Coupon { // phiếu giảm giá.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private int couponId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;  // Mã phiếu giảm giá

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;  // Ngày hết hạn

    @Column(name = "discount_value", nullable = false)
    private double discountValue;  // Giá trị giảm giá

    public Coupon() {

    }

    public Coupon(int couponId, User user, String code, Date expirationDate, double discountValue) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
