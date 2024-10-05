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

import java.util.Optional;

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

    @Override
    public boolean existsByRoleIdAndPermissionIdAndActionAndStatus(int roleId, int permissionId, RolePermissionActionEnum action, RolePermissionEnum status) {
        return rolePermissionRepository.existsByRole_RoleIdAndPermission_PermissionIdAndActionAndStatus(roleId, permissionId, action, status);
    }

    @Override
    public void updateRolePermission(RolePermission rolePermission) {
        // Kiểm tra xem RolePermission có tồn tại trong cơ sở dữ liệu không
        Optional<RolePermission> existingRolePermission = rolePermissionRepository.findById(rolePermission.getRolePermissionId());

        if (!existingRolePermission.isPresent()) {
            throw new RuntimeException("RolePermission not found for ID: " + rolePermission.getRolePermissionId());
        }

        // Cập nhật các thuộc tính cần thiết (chỉ cần cập nhật trạng thái active)
        RolePermission updatedRolePermission = existingRolePermission.get();
        updatedRolePermission.setStatus(RolePermissionEnum.ACTIVE);

        rolePermissionRepository.save(updatedRolePermission);
    }
}
