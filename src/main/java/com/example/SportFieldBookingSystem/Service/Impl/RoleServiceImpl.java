package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.Entity.Role;

public interface RoleServiceImpl {
    public RoleDTO getRoleByRoleId(int roleId);
    public Role getRoleByRoleId_ReturnRole(int roleId);
}
