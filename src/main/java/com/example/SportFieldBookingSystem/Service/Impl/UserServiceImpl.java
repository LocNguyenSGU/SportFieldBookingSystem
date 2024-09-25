package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.User;

import java.util.List;

public interface UserServiceImpl {
    public List<UserBasicDTO> getAllUser();
    public UserBasicDTO getUserByUserCode(String userCode);

    public UserBasicDTO findUserWithRolesByUserCode(String userCode);

    public List<UserBasicDTO> findAllUsersWithRoles();

    public void updateUser(String userCode, UserUpdateDTO userUpdateDTO);

    public boolean existsUserByUsername(String userName);

    public boolean existsUserByEmail(String email);


}
