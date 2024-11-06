package com.example.SportFieldBookingSystem.Mapper;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleResponseDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public UserBasicDTO toBasicDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserBasicDTO userBasicDTO = new UserBasicDTO();
        userBasicDTO.setUserId(user.getUserId());
        userBasicDTO.setUsername(user.getUsername());
        userBasicDTO.setUserCode(user.getUserCode());
        userBasicDTO.setEmail(user.getEmail());
        userBasicDTO.setFullName(user.getFullName());
        userBasicDTO.setThoiGianTao(user.getThoiGianTao());
        userBasicDTO.setPhone(user.getPhone());
        userBasicDTO.setStatus(String.valueOf(user.getStatus()));
        userBasicDTO.setRole(RoleMapper.toRoleResponseDTO(user.getUserRoleList().getFirst().getRole()));
        return userBasicDTO;
    }
}
