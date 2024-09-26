package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserServiceImpl {
    public List<UserBasicDTO> getAllUser();
    public UserBasicDTO getUserByUserCode(String userCode);

    public UserBasicDTO findUserWithRolesByUserCode(String userCode);

    public UserBasicDTO findUserWithRolesByUserName(String username);

    public Page<UserBasicDTO> findAllUsersWithRoles(int page, int size);

    public void updateUser(String userCode, UserUpdateDTO userUpdateDTO);

    public boolean existsUserByUsername(String userName);

    public boolean existsUserByEmail(String email);

    public boolean createUser(UserCreateDTO userCreateDTO);


}
