package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.AuthDTO.SignupDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserBasicDTO> getAllUser();

    UserBasicDTO getUserByUserCode(String userCode);

    UserBasicDTO findUserWithRolesByUserCode(String userCode);

    UserBasicDTO findUserWithRolesByUserName(String username);

    Page<UserBasicDTO> findAllUsersWithRoles(int page, int size);

    void updateUser(int userId, UserUpdateDTO userUpdateDTO);

    boolean updateUser(UserUpdateDTO userUpdateDTO);

    boolean existsUserByUsername(String userName);

    boolean existsUserByEmail(String email);

    Optional<UserBasicDTO> getUserByUsername(String userName);

    boolean createUser(UserCreateDTO userCreateDTO);

    boolean createUserSignUp(SignupDTO signupDTO);
    String createPasswordResetToken(String email);

    UserBasicDTO getUserByEmail(String email);

    Optional<User> getUserEntityByEmail(String email);

    Page<UserBasicDTO> getByUsernameEmailAndRole(String userName, String email, String roleName, int page, int size);

    UserBasicDTO getUserByUserId(int userId);

}
