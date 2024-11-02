package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.*;
import com.example.SportFieldBookingSystem.Entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    RoleDTO getRoleByRoleId(int roleId);
    Role getRoleByRoleId_ReturnRole(int roleId);
    Role createRole(RoleDTO roleDTO);

    boolean createRoleWithPermission(RoleCreateDTO roleCreateDTO);

    boolean updateRoleWithPermission(RoleUpdateDTO roleUpdateDTO);
    boolean existsByRoleName(String roleName);

    RoleByUserDTO
    getListRoleByUserRoleList_User_UserName(String userName);

    int getRoleIdByRoleName(String roleName);

    List<RoleResponseDTO> getAllRole();

    Page<RoleResponseDTO> searchRoleByRoleName(String roleName, int page, int size);

    RoleResponseDTO getRoleDetailById(int roleId);
    boolean existsByRoleNameAndNotRoleIdNot(String roleName, int roleId);


}
