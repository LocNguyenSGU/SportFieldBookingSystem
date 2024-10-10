package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServiceImpl {
    public List<UserResponseDTO> getAllUser();
    public UserResponseDTO getUserByUserCode(String userCode);
}
