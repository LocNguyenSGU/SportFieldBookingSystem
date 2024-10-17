package com.example.SportFieldBookingSystem.DTO.UserDTO;

import java.util.List;

public class UserUpdateDTO {
    private int userId;
    private String userCode;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String status;
    private List<Integer> roleIdList;
    public UserUpdateDTO() {

    }

    public UserUpdateDTO(int userId, String userCode, String username, String fullName, String email, String password, String phone, String status, List<Integer> roleIdList) {
        this.userId = userId;
        this.userCode = userCode;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.status = status;
        this.roleIdList = roleIdList;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
