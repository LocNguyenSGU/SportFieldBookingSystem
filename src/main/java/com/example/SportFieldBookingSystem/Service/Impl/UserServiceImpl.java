package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserResponseDTO;

import java.util.List;

public interface UserServiceImpl {
    public List<UserResponseDTO> getAllUser();
    public UserResponseDTO getUserByUserCode(String userCode);
}
