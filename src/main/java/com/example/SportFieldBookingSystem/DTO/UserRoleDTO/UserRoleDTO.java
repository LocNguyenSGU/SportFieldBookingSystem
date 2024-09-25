package com.example.SportFieldBookingSystem.DTO.UserRoleDTO;

public class UserRoleDTO {
    private int userRoleId;
    private int role_id;
    private int user_id;


    public UserRoleDTO() {

    }

    public UserRoleDTO(int userRoleId, int role_id, int user_id) {
        this.userRoleId = userRoleId;
        this.role_id = role_id;
        this.user_id = user_id;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
