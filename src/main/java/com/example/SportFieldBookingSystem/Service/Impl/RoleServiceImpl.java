package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.*;
import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;
import com.example.SportFieldBookingSystem.Entity.Permission;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.RolePermission;
import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionEnum;
import com.example.SportFieldBookingSystem.Repository.PermissionRepository;
import com.example.SportFieldBookingSystem.Repository.RolePermissionRepository;
import com.example.SportFieldBookingSystem.Repository.RoleRepository;
import com.example.SportFieldBookingSystem.Mapper.RoleMapper;
import com.example.SportFieldBookingSystem.Service.PermissionService;
import com.example.SportFieldBookingSystem.Service.RolePermissionService;
import com.example.SportFieldBookingSystem.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Lazy
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    private RoleMapper roleMapper = new RoleMapper();
    @Autowired
    @Lazy
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

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
            role.setTrangThaiActive(roleDTO.getActiveEnum());
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
            roleDTO.setActiveEnum(roleCreateDTO.getActiveEnum());
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
    public boolean updateRoleWithPermission(RoleUpdateDTO roleUpdateDTO) {
        // Lấy role cần cập nhật từ cơ sở dữ liệu
        Role role = roleRepository.findRoleByRoleId(roleUpdateDTO.getRoleId());
        if (role == null) {
            throw new RuntimeException("Role not found");
        }

        // Kiểm tra tên role nếu có và nếu khác với tên hiện tại
        if (roleUpdateDTO.getRoleName() != null && !roleUpdateDTO.getRoleName().equals(role.getRoleName())) {
            // Kiểm tra xem tên role mới có trùng với bất kỳ role nào khác không
            boolean isRoleNameTaken = roleRepository.existsByRoleNameAndRoleIdNot(roleUpdateDTO.getRoleName(), role.getRoleId());
            if (isRoleNameTaken) {
                throw new RuntimeException("Role name already exists");
            }

            // Cập nhật tên role nếu tên không trùng
            role.setRoleName(roleUpdateDTO.getRoleName());
        }
        role.setTrangThaiActive(roleUpdateDTO.getActiveEnum());
        roleRepository.save(role);

        // Lấy danh sách quyền hiện tại của role
        List<RolePermission> currentRolePermissions = role.getRolePermissionList();

        // Tạo một tập hợp (Set) để lưu các cặp quyền mới (permissionId, action)
        Set<String> newPermissionsSet = new HashSet<>();
        for (RolePermissionCreateDTO rolePermissionCreateDTO : roleUpdateDTO.getRolePermissionDTOList()) {
            int permissionId = rolePermissionCreateDTO.getPermissionId();
            RolePermissionActionEnum action = rolePermissionCreateDTO.getAction();
            System.out.println("Permission ID: " + permissionId + ", Action: " + action);
            String key = permissionId + "_" + action;
            newPermissionsSet.add(key);
        }

        // Danh sách để thêm mới quyền
        List<RolePermission> permissionsToAdd = new ArrayList<>();

        // So sánh quyền cũ với quyền mới
        for (RolePermission existingPermission : currentRolePermissions) {
            int permissionId = existingPermission.getPermission().getPermissionId();
            String action = String.valueOf(existingPermission.getAction());

            // Tạo chuỗi để so sánh trong quyền mới
            String existingKey = permissionId + "_" + action;

            // Nếu quyền cũ không có trong quyền mới, set trạng thái thành INACTIVE
            if (!newPermissionsSet.contains(existingKey)) {
                existingPermission.setStatus(RolePermissionEnum.INACTIVE);
                rolePermissionRepository.save(existingPermission);
            } else {
                // Nếu quyền vẫn còn trong danh sách mới, giữ trạng thái là ACTIVE
                existingPermission.setStatus(RolePermissionEnum.ACTIVE);
                rolePermissionRepository.save(existingPermission);
                newPermissionsSet.remove(existingKey);  // Xóa khỏi set để xử lý quyền mới còn lại
            }
        }

        // Những quyền còn lại trong newPermissionsSet là quyền mới, thêm chúng vào
        for (String newPermissionKey : newPermissionsSet) {
            // Tách chuỗi key ra để lấy permissionId và action
            String[] parts = newPermissionKey.split("_");
            int permissionId = Integer.parseInt(parts[0]);
            System.out.println("ID cac quyen moi la: " + permissionId);
            String action = parts[1];

            // Tạo mới quyền
            RolePermissionCreateDTO rolePermissionCreateDTO = new RolePermissionCreateDTO();
            rolePermissionCreateDTO.setPermissionId(permissionId);
            rolePermissionCreateDTO.setAction(RolePermissionActionEnum.valueOf(action));
            rolePermissionCreateDTO.setRoleId(roleUpdateDTO.getRoleId());
            rolePermissionService.createRolePermission(rolePermissionCreateDTO);
        }

        return true;
    }



    @Override
    public boolean existsByRoleName(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

    @Override
    public RoleByUserDTO getListRoleByUserRoleList_User_UserName(String userName) {
        try{
            List<Role> roleList = roleRepository.findListRoleByUserRoleList_User_Username(userName);
            RoleByUserDTO roleByUserDTO = new RoleByUserDTO();
            if(roleList == null) {
                return null;
            }
            roleByUserDTO.setUserName(userName);
            for(Role role : roleList) {
                roleByUserDTO.setRoleName(role.getRoleName());
                break;
            }
            return roleByUserDTO;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public int getRoleIdByRoleName(String roleName) {
        try {
            return roleRepository.findRoleIdByRoleName(roleName);
        }catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    @Override
    public List<RoleResponseDTO> getAllRole() {
        List<Role> roleList = roleRepository.findAllByRolePermissionList_status(RolePermissionEnum.ACTIVE);
        return roleList.stream()
                .map(RoleMapper::toRoleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RoleResponseDTO> searchRoleByRoleName(String roleName, int page, int size) {

        Page<Role> rolePage = roleRepository.findByRoleNameContainingIgnoreCase(roleName, PageRequest.of(page, size));
        return rolePage.map(RoleMapper::toRoleResponseDTO);
    }

    @Override
    public RoleResponseDTO getRoleDetailById(int roleId) {
        Role role = roleRepository.findRoleByRoleId(roleId);
        role.setRolePermissionList(
                role.getRolePermissionList().stream()
                        .filter(rp -> rp.getStatus() == RolePermissionEnum.ACTIVE)
                        .collect(Collectors.toList())
        );
        return RoleMapper.toRoleResponseDTO(role);
    }

    @Override
    public boolean existsByRoleNameAndNotRoleIdNot(String roleName, int roleId) {
        return roleRepository.existsByRoleNameAndRoleIdNot(roleName, roleId);
    }
}
