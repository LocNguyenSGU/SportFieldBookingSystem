package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.UserEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="user")
public class User { // nguoi dung

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_code", unique = true, length = 10, updatable = false)
    private String userCode;

    @Column(name = "user_name", nullable = false, length = 30)
    private String username;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private UserEnum status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoleList;
    @OneToMany(mappedBy = "user")
    private List<Booking> bookingList;

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "user")
    private List<Favorite> favoriteList;

    @OneToMany(mappedBy = "user")
    private List<Notification> notificationList ;

    @OneToMany(mappedBy = "user")
    private List<Coupon> couponList ;

    public User() {

    }

    public User(int userId, String userCode, String username, String password, String fullName, String email, String phone, UserEnum status, List<UserRole> userRoleList, List<Booking> bookingList, List<Review> reviewList, List<Favorite> favoriteList, List<Notification> notificationList, List<Coupon> couponList) {
        this.userId = userId;
        this.userCode = userCode;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.userRoleList = userRoleList;
        this.bookingList = bookingList;
        this.reviewList = reviewList;
        this.favoriteList = favoriteList;
        this.notificationList = notificationList;
        this.couponList = couponList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserEnum getStatus() {
        return status;
    }

    public void setStatus(UserEnum status) {
        this.status = status;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }
}
