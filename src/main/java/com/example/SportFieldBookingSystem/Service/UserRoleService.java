package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserRoleDTO.UserRoleDTO;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import com.example.SportFieldBookingSystem.Repository.UserRoleRepository;
import com.example.SportFieldBookingSystem.Service.Impl.UserRoleServiceImpl;
import com.example.SportFieldBookingSystem.Service.Mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService implements UserRoleServiceImpl {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public List<UserRoleDTO> getUserRoleByUser_UserId(int userId) {
        List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
        try {
            List<UserRole> userRoleList = userRoleRepository.findUserRoleByUser_UserId(userId);
            userRoleDTOList = userRoleList.stream()
                    .map(userRoleMapper::toUserRoleDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error fetching all users: " + e.getMessage());
            // Optionally, throw a custom exception
            throw new RuntimeException("Unable to fetch users", e);
        }
        return userRoleDTOList;
    }

    @Override
    public boolean saveUserRole(UserRole userRole) {
        try{
            userRoleRepository.save(userRole);
            return true;
        }catch (Exception e) {
            // Có thể log lỗi tại đây nếu cần thiết
            System.err.println("Error creating user: " + e.getMessage());
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
}
