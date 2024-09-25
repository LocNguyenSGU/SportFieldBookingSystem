package com.example.SportFieldBookingSystem.Service.Mapper;

import com.example.SportFieldBookingSystem.DTO.UserRoleDTO.UserRoleDTO;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleDTO toUserRoleDTO(UserRole userRole);
}
