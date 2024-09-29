package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.RoleRepository;
import com.example.SportFieldBookingSystem.Service.Impl.PermissionServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.RoleServiceImpl;
import com.example.SportFieldBookingSystem.Service.Mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface RoleService {

    RoleDTO getRoleByRoleId(int roleId);
    Role getRoleByRoleId_ReturnRole(int roleId);
    Role createRole(RoleDTO roleDTO);

    public boolean createRoleWithPermission(RoleCreateDTO roleCreateDTO);
    public boolean existsByRoleName(String roleName);

}
