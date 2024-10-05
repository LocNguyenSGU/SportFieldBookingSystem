package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.AuthDTO.SignupDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserService {
    List<UserBasicDTO> getAllUser();
    UserBasicDTO getUserByUserCode(String userCode);

    UserBasicDTO findUserWithRolesByUserCode(String userCode);

    UserBasicDTO findUserWithRolesByUserName(String username);

    Page<UserBasicDTO> findAllUsersWithRoles(int page, int size);

    void updateUser(String userCode, UserUpdateDTO userUpdateDTO);

    boolean existsUserByUsername(String userName);

    boolean existsUserByEmail(String email);

    boolean createUser(UserCreateDTO userCreateDTO);

    boolean createUserSignUp(SignupDTO signupDTO);



}
