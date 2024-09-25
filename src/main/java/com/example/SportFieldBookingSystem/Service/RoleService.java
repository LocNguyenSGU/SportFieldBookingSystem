package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.RoleRepository;
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
}
