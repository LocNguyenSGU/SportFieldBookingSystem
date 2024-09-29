package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;
import com.example.SportFieldBookingSystem.Entity.Permission;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.RolePermission;
import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionEnum;
import com.example.SportFieldBookingSystem.Repository.RolePermissionRepository;
import com.example.SportFieldBookingSystem.Service.PermissionService;
import com.example.SportFieldBookingSystem.Service.RolePermissionService;
import com.example.SportFieldBookingSystem.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Override
    public boolean createRolePermission(RolePermissionCreateDTO rolePermissionCreateDTO) {
        RolePermission rolePermission = new RolePermission();
        try {
            Role role = roleService.getRoleByRoleId_ReturnRole(rolePermissionCreateDTO.getRoleId());
            Permission permission = permissionService.getPermissionByPermissionId(rolePermissionCreateDTO.getPermissionId());
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
            rolePermission.setAction(RolePermissionActionEnum.valueOf(rolePermissionCreateDTO.getAction().name()));
            rolePermission.setStatus(RolePermissionEnum.ACTIVE);
            rolePermissionRepository.save(rolePermission);
            return true;
        }catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
