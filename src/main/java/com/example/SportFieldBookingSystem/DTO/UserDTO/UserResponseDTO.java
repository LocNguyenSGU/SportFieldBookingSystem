package com.example.SportFieldBookingSystem.DTO.UserDTO;

public class UserResponseDTO {
    private int userId;
    private String userCode;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String status;

    public UserResponseDTO() {

    }

    public UserResponseDTO(int userId, String userCode, String username, String fullName, String email, String phone, String status) {
        this.userId = userId;
        this.userCode = userCode;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
