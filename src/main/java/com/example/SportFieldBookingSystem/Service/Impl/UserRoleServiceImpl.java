package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.UserRoleDTO.UserRoleDTO;
import com.example.SportFieldBookingSystem.Entity.UserRole;

import java.util.List;

public interface UserRoleServiceImpl {
    public List<UserRoleDTO> getUserRoleByUser_UserId(int userId);
}
