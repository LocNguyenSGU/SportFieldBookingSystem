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
