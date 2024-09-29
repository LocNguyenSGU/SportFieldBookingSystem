package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.AuthDTO.SignupDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import com.example.SportFieldBookingSystem.Enum.UserEnum;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Repository.UserRoleRepository;
import com.example.SportFieldBookingSystem.Service.Impl.RoleServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.UserRoleServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import com.example.SportFieldBookingSystem.Service.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface UserService {
    public List<UserBasicDTO> getAllUser();
    public UserBasicDTO getUserByUserCode(String userCode);

    public UserBasicDTO findUserWithRolesByUserCode(String userCode);

    public UserBasicDTO findUserWithRolesByUserName(String username);

    public Page<UserBasicDTO> findAllUsersWithRoles(int page, int size);

    public void updateUser(String userCode, UserUpdateDTO userUpdateDTO);

    public boolean existsUserByUsername(String userName);

    public boolean existsUserByEmail(String email);

    public boolean createUser(UserCreateDTO userCreateDTO);

    public boolean createUserSignUp(SignupDTO signupDTO);



}
