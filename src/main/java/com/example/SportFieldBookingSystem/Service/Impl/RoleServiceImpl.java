package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Repository.RoleRepository;
import com.example.SportFieldBookingSystem.Service.Mapper.RoleMapper;
import com.example.SportFieldBookingSystem.Service.PermissionService;
import com.example.SportFieldBookingSystem.Service.RolePermissionService;
import com.example.SportFieldBookingSystem.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Lazy
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    @Lazy
    private RolePermissionService rolePermissionService;
    @Override
    public RoleDTO getRoleByRoleId(int roleId) {
        RoleDTO roleDTO = new RoleDTO();
        try {
            Role role = roleRepository.findRoleByRoleId(roleId);
            roleDTO = roleMapper.toRoleDTO(role);
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error fetching roles: " + e.getMessage());
            // Optionally, throw a custom exception
            throw new RuntimeException("Unable to fetch roles", e);
        }
        return roleDTO;
    }

    @Override
    public Role getRoleByRoleId_ReturnRole(int roleId) {
        return roleRepository.findRoleByRoleId(roleId);
    }

    @Override
    public Role createRole(RoleDTO roleDTO) {
        try {
            Role role = new Role();
            role.setRoleName(roleDTO.getRoleName());
            Role roleNew = roleRepository.save(role);
            return roleNew;
        } catch (Exception e) {
            System.out.println("Loi khi them role: " + e);
        }
        return null;
    }

    @Override
    public boolean createRoleWithPermission(RoleCreateDTO roleCreateDTO) {
        try{
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRoleName(roleCreateDTO.getRoleName());
            Role roleNew = createRole(roleDTO);
            List<RolePermissionCreateDTO> rolePermissionDTOList = roleCreateDTO.getRolePermissionDTOList();
            for(RolePermissionCreateDTO r : rolePermissionDTOList) {
                RolePermissionCreateDTO rolePermissionCreateDTO = new RolePermissionCreateDTO();
                rolePermissionCreateDTO.setRoleId(roleNew.getRoleId()); // lay tu cai role moi tao
                rolePermissionCreateDTO.setAction(r.getAction()); // lay tu FE
                rolePermissionCreateDTO.setPermissionId(r.getPermissionId()); // lay tu FE

                rolePermissionService.createRolePermission(rolePermissionCreateDTO);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }
}
