package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionDTO;
import com.example.SportFieldBookingSystem.Entity.RolePermission;

public class RolePermissionMapper {
    public static RolePermissionDTO toRolePermissionDTO(RolePermission rolePermission) {
        RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
        rolePermissionDTO.setPermissionName(rolePermission.getPermission().getPermissionName());
        rolePermissionDTO.setPermissionId(rolePermission.getPermission().getPermissionId());
        rolePermissionDTO.setAction(rolePermission.getAction());
        rolePermissionDTO.setTrangThaiActive(rolePermission.getStatus());
        return rolePermissionDTO;
    }
}
