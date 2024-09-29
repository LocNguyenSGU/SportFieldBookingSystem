package com.example.SportFieldBookingSystem.DTO.RoleDTO;

import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;
import com.example.SportFieldBookingSystem.Entity.RolePermission;

import java.util.List;

public class RoleCreateDTO {
    private String roleName;
    private List<RolePermissionCreateDTO> rolePermissionDTOList;

    public RoleCreateDTO() {

    }
    public RoleCreateDTO(String roleName, List<RolePermissionCreateDTO> rolePermissionDTOList) {
        this.roleName = roleName;
        this.rolePermissionDTOList = rolePermissionDTOList;
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
