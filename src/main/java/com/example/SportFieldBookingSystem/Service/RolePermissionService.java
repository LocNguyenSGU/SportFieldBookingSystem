package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;
import com.example.SportFieldBookingSystem.Entity.Permission;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.RolePermission;
import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionEnum;
import com.example.SportFieldBookingSystem.Repository.RolePermissionRepository;
import com.example.SportFieldBookingSystem.Service.Impl.PermissionServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface RolePermissionService {

    boolean createRolePermission(RolePermissionCreateDTO rolePermissionCreateDTO);
}
