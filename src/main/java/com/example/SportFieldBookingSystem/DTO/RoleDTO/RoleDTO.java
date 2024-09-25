package com.example.SportFieldBookingSystem.DTO.RoleDTO;

import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.RolePermission;

import java.util.List;

public class RoleDTO {
    private int roleId;
    private String roleName;

    public RoleDTO(){}

    // Constructor
    public RoleDTO(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    // Getters and Setters
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
