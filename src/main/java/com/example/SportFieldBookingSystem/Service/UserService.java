package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserResponseDTO;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService implements UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponseDTO> getAllUser() {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        try {
            List<User> userList = userRepository.findAll();
            for (User user : userList) {
                UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setUserId(user.getUserId());
                userResponseDTO.setUserCode(user.getUserCode());
                userResponseDTO.setEmail(user.getEmail());
                userResponseDTO.setUsername(user.getUsername());
                userResponseDTO.setFullName(user.getFullName());
                userResponseDTO.setPhone(user.getPhone());
                userResponseDTO.setStatus(user.getStatus().toString());
                userResponseDTOList.add(userResponseDTO);
            }
        } catch (Exception e) {
            // Log the error message for debugging
            System.err.println("Error fetching all users: " + e.getMessage());
            // Optional: you can throw a custom exception here
        }
        return userResponseDTOList;
    }

    @Override
    public UserResponseDTO getUserByUserCode(String userCode) {
        return null;
    }


}
