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

public interface UserRoleService {
    List<UserRoleDTO> getUserRoleByUser_UserId(int userId);
    boolean saveUserRole(UserRole userRole);
}
