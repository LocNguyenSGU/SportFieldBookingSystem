package com.example.SportFieldBookingSystem.Service.Mapper;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toRoleDTO(Role role);
}
