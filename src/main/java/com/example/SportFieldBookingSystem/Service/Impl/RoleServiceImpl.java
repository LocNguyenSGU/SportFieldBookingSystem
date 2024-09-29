package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.Entity.Role;

public interface RoleServiceImpl {
    RoleDTO getRoleByRoleId(int roleId);
    Role getRoleByRoleId_ReturnRole(int roleId);
    Role createRole(RoleCreateDTO roleCreateDTO);

    public boolean createRoleWithPermission(RoleCreateDTO roleCreateDTO);
}
