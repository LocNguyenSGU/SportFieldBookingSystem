package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.RoleRepository;
import com.example.SportFieldBookingSystem.Service.Impl.PermissionServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.RolePermissionImpl;
import com.example.SportFieldBookingSystem.Service.Impl.RoleServiceImpl;
import com.example.SportFieldBookingSystem.Service.Mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements RoleServiceImpl {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionServiceImpl permissionServiceImpl;
    @Autowired
    private RolePermissionImpl rolePermissionImpl;
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
    public Role createRole(RoleCreateDTO roleCreateDTO) {
        try {
                Role role = new Role();
                role.setRoleName(roleCreateDTO.getRoleName());
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
            Role roleNew = createRole(roleCreateDTO);
            List<RolePermissionCreateDTO> rolePermissionDTOList = roleCreateDTO.getRolePermissionDTOList();
            for(RolePermissionCreateDTO r : rolePermissionDTOList) {
                RolePermissionCreateDTO rolePermissionCreateDTO = new RolePermissionCreateDTO();
                rolePermissionCreateDTO.setRoleId(roleNew.getRoleId());
                rolePermissionCreateDTO.setAction(r.getAction());
                rolePermissionCreateDTO.setPermissionId(r.getPermissionId());
                rolePermissionImpl.createRolePermission(rolePermissionCreateDTO);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

}
