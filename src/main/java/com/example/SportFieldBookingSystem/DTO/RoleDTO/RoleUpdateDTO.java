package com.example.SportFieldBookingSystem.DTO.RoleDTO;

import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;

import java.util.List;

public class RoleUpdateDTO {
    private int roleId;
    private String roleName;
    private List<RolePermissionCreateDTO> rolePermissionDTOList;

    public RoleUpdateDTO() {

    }

    public RoleUpdateDTO(int roleId, String roleName, List<RolePermissionCreateDTO> rolePermissionDTOList) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.rolePermissionDTOList = rolePermissionDTOList;
    }

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

    public List<RolePermissionCreateDTO> getRolePermissionDTOList() {
        return rolePermissionDTOList;
    }

    public void setRolePermissionDTOList(List<RolePermissionCreateDTO> rolePermissionDTOList) {
        this.rolePermissionDTOList = rolePermissionDTOList;
    }
}
