package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.UserRoleDTO.UserRoleDTO;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import org.mapstruct.Mapper;

public class UserRoleMapper {

    public UserRoleDTO toUserRoleDTO(UserRole userRole) {
        if ( userRole == null ) {
            return null;
        }

        UserRoleDTO userRoleDTO = new UserRoleDTO();

        if ( userRole.getUserRoleId() != null ) {
            userRoleDTO.setUserRoleId( userRole.getUserRoleId() );
        }

        return userRoleDTO;
    }
}
