package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.UserRoleDTO.UserRoleDTO;
import com.example.SportFieldBookingSystem.Entity.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRoleDTO> getUserRoleByUser_UserId(int userId);
    boolean saveUserRole(UserRole userRole);
}
