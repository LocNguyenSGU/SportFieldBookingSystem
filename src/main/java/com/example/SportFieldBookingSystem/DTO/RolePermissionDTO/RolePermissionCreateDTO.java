package com.example.SportFieldBookingSystem.DTO.RolePermissionDTO;

import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;

public class RolePermissionCreateDTO {
    private int permissionId;
    private int roleId;
    private RolePermissionActionEnum action;

    public RolePermissionCreateDTO() {

    }

    public RolePermissionCreateDTO(int permissionId, int roleId, RolePermissionActionEnum action) {
        this.permissionId = permissionId;
        this.roleId = roleId;
        this.action = action;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public RolePermissionActionEnum getAction() {
        return action;
    }

    public void setAction(RolePermissionActionEnum action) {
        this.action = action;
    }
}
