package com.example.SportFieldBookingSystem.Security;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleByUserDTO;
import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionEnum;
import com.example.SportFieldBookingSystem.Service.PermissionService;
import com.example.SportFieldBookingSystem.Service.RolePermissionService;
import com.example.SportFieldBookingSystem.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("customPermissionEvaluator")
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object action) {
        String username = authentication.getName();
        String permissionName = (String) targetDomainObject;
        String actionName = (String) action;

        // Lấy các role của người dùng từ bảng UserRole
        RoleByUserDTO roleByUserDTO = roleService.getListRoleByUserRoleList_User_UserName(username);
        String userRoleName = roleByUserDTO.getRoleName();
        int roleId = roleService.getRoleIdByRoleName(userRoleName);
        int permissionId = permissionService.getPermissionIdByPermissionName(permissionName);
        if (rolePermissionService.existsByRoleIdAndPermissionIdAndActionAndStatus(roleId, permissionId, RolePermissionActionEnum.valueOf(actionName), RolePermissionEnum.ACTIVE)) {
            return true;
        }
        return false;
    }


    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
