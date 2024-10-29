package com.example.SportFieldBookingSystem.DTO.UserDTO;

import com.example.SportFieldBookingSystem.DTO.UserRoleDTO.UserRoleDTO;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicDTO {
    private int userId;
    private String userCode;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String status;
    private List<String> roleNameList;
}