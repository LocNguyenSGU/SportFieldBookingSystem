package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;
import com.example.SportFieldBookingSystem.Entity.Permission;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.RolePermission;
import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionEnum;
import com.example.SportFieldBookingSystem.Repository.RolePermissionRepository;
import com.example.SportFieldBookingSystem.Service.Impl.PermissionServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.RolePermissionImpl;
import com.example.SportFieldBookingSystem.Service.Impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService implements RolePermissionImpl {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private RoleServiceImpl roleServiceImpl;
    @Autowired
    private PermissionServiceImpl permissionServiceImpl;
    @Override
    public boolean createRolePermission(RolePermissionCreateDTO rolePermissionCreateDTO) {
        RolePermission rolePermission = new RolePermission();
        try {
            Role role = roleServiceImpl.getRoleByRoleId_ReturnRole(rolePermissionCreateDTO.getRoleId());
            Permission permission = permissionServiceImpl.getPermissionByPermissionId(rolePermissionCreateDTO.getPermissionId());
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
