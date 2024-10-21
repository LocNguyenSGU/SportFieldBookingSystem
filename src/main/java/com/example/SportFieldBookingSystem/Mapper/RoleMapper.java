package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import org.mapstruct.Mapper;

public class RoleMapper {
    public  RoleDTO toRoleDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setRoleId( role.getRoleId() );
        roleDTO.setRoleName( role.getRoleName() );

        return roleDTO;
    }
}
