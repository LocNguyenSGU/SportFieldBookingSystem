package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleResponseDTO;
import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.RolePermission;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RoleMapper {

    public RoleDTO toRoleDTO(Role role) {
        if ( role == null ) {
            return null;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId( role.getRoleId() );
        roleDTO.setRoleName( role.getRoleName() );

        return roleDTO;
    }
    public static RoleResponseDTO toRoleResponseDTO(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setTenQuyen(role.getRoleName());
        roleResponseDTO.setIdQuyen(role.getRoleId());
        roleResponseDTO.setTrangThaiActive(role.getTrangThaiActive());

        List<RolePermission> rolePermissionList = role.getRolePermissionList();
        if (rolePermissionList == null) {
            rolePermissionList = new ArrayList<>();
        }

        List<RolePermissionDTO> rolePermissionDTOList = rolePermissionList.stream()
                .filter(Objects::nonNull)
                .map(RolePermissionMapper::toRolePermissionDTO)
                .collect(Collectors.toList());

        roleResponseDTO.setRolePermissionDTOList(rolePermissionDTOList);
        return roleResponseDTO;
    }
}
