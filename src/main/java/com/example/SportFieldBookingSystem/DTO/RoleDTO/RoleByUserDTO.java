package com.example.SportFieldBookingSystem.DTO.RoleDTO;

import java.util.List;

public class RoleByUserDTO {
    private String userName;
    private String roleName;

   public RoleByUserDTO(){}

    public RoleByUserDTO(String userName, String roleName) {
        this.userName = userName;
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
