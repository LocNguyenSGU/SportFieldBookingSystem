package com.example.SportFieldBookingSystem.DTO.UserRoleDTO;

public class UserRoleDTO {
    private int userRoleId;
    private int roleId;
    private int userId;


    public UserRoleDTO() {

    }

    public UserRoleDTO(int userRoleId, int roleId, int userId) {
        this.userRoleId = userRoleId;
        this.roleId = roleId;
        this.userId = userId;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
