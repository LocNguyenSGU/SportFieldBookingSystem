package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.PermissionDTO.PermissionDTO;
import com.example.SportFieldBookingSystem.Entity.Permission;

public class PermissionMapper {
    public static PermissionDTO toPermissionDTO(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setPermissionId(permission.getPermissionId());
        permissionDTO.setPermissionName(permission.getPermissionName());
        return permissionDTO;
    }
}
