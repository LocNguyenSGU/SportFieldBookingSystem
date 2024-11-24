package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.UserEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User { // nguoi dung

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_code", unique = true, length = 30, updatable = false)
    private String userCode;

    @Column(name = "password", length = 90)
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

    @OneToMany(mappedBy = "user")
    private List<Field> fieldList;

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

    @Column(name="refresh_password_token")
    private String refreshPasswordToken;

    @Column(name = "is_login_google", nullable = false)
    private int isLoginGoogle = 0;

    @Column(name = "is_login_github", nullable = false)
    private int isLoginGithub= 0;

    @Column(name="date_create")
    private LocalDateTime thoiGianTao;
}
