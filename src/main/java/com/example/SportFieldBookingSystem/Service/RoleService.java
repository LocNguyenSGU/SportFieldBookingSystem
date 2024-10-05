package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleByUserDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;

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


}
