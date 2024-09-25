package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import com.example.SportFieldBookingSystem.Enum.UserEnum;
import com.example.SportFieldBookingSystem.Repository.RoleRepository;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Repository.UserRoleRepository;
import com.example.SportFieldBookingSystem.Service.Impl.RoleServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import com.example.SportFieldBookingSystem.Service.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Override
//    public List<UserResponseDTO> getAllUser() {
//        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
//        try {
//            List<User> userList = userRepository.findAll();
//            for (User user : userList) {
//                UserResponseDTO userResponseDTO = new UserResponseDTO();
//                userResponseDTO.setUserId(user.getUserId());
//                userResponseDTO.setUserCode(user.getUserCode());
//                userResponseDTO.setEmail(user.getEmail());
//                userResponseDTO.setUsername(user.getUsername());
//                userResponseDTO.setFullName(user.getFullName());
//                userResponseDTO.setPhone(user.getPhone());
//                userResponseDTO.setStatus(user.getStatus().toString());
//                userResponseDTOList.add(userResponseDTO);
//            }
//        } catch (Exception e) {
//            // Log the error message for debugging
//            System.err.println("Error fetching all users: " + e.getMessage());
//            // Optional: you can throw a custom exception here
//        }
//        return userResponseDTOList;
//    }

    // B1: xac dinh api tra ve cai gi
    // B2: lam cac lop service tra ve tuong ung
    // B3: ket noi cac service lai de no tra ve dung
    public List<UserBasicDTO> getAllUser() {
        List<UserBasicDTO> userResponseDTOList = new ArrayList<>();
        try {
            List<User> userList = userRepository.findAll();
            userResponseDTOList = userList.stream()
                    .map(userMapper::toBasicDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error fetching all users: " + e.getMessage());
            // Optionally, throw a custom exception
            throw new RuntimeException("Unable to fetch users", e);
        }
        return userResponseDTOList;
    }


    @Override
    public UserBasicDTO getUserByUserCode(String userCode) {
        return null;
    }

    @Override
    public UserBasicDTO findUserWithRolesByUserCode(String userCode) {
        try {
            // Tìm người dùng dựa trên userCode
            Optional<User> userOptional = userRepository.findUserWithRolesByUserCode(userCode);

            // Kiểm tra xem người dùng có tồn tại hay không
            if (userOptional.isPresent()) {
                User user = userOptional.get(); // Lấy người dùng
                return userMapper.toBasicDTO(user); // Chuyển đổi sang DTO và trả về
            } else {
                // Xử lý khi không tìm thấy người dùng
                System.out.println("Không tìm thấy người dùng với userCode: " + userCode);
                return null; // Hoặc ném ngoại lệ tùy theo yêu cầu
            }
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error fetching user: " + e.getMessage());
            // Ném ra một ngoại lệ tùy chọn
            throw new RuntimeException("Unable to fetch user", e);
        }
    }

    @Override
    public List<UserBasicDTO> findAllUsersWithRoles() {
        List<UserBasicDTO> userBasicDTOList = new ArrayList<>();
        try {
            List<User> userList = userRepository.findAllUsersWithRoles();
            userBasicDTOList = userList.stream()
                    .map(userMapper::toBasicDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error fetching all users: " + e.getMessage());
            // Optionally, throw a custom exception
            throw new RuntimeException("Unable to fetch users", e);
        }
        return userBasicDTOList;
    }

    @Override
    @Transactional
    public void updateUser(String userCode, UserUpdateDTO userUpdateDTO) {
        Optional<User> optionalUser = userRepository.findUserWithRolesByUserCode(userCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if(userUpdateDTO.getUsername() != null) {
                if(existsUserByUsername(userUpdateDTO.getUsername())){
                    throw new RuntimeException("Username already exists.");
                }
            }
            if(userUpdateDTO.getEmail() != null) {
                if(existsUserByEmail(userUpdateDTO.getEmail())){
                    throw new RuntimeException("Email already exists.");
                }
            }

            // Cập nhật thông tin người dùng
            user.setUsername(userUpdateDTO.getUsername());
            user.setEmail(userUpdateDTO.getEmail());
            user.setPhone(userUpdateDTO.getPhone());
            user.setFullName(userUpdateDTO.getFullName());
            user.setPassword(userUpdateDTO.getPassword());

            // Cập nhật trạng thái
            if (userUpdateDTO.getStatus() != null) {
                try {
                    user.setStatus(UserEnum.valueOf(userUpdateDTO.getStatus().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid status value: " + userUpdateDTO.getStatus());
                }
            }

            // Lấy danh sách vai trò hiện tại và danh sách moi của người dùng
            List<UserRole> existingRoles = user.getUserRoleList();
            List<Integer> newRoleIdList = userUpdateDTO.getRoleIdList();

            // Xóa các vai trò không còn trong danh sách vai trò mới
            existingRoles.removeIf(existingRole -> !newRoleIdList.contains(existingRole.getRole().getRoleId()));

            // Thêm các vai trò mới chưa có
            for (Integer roleId : newRoleIdList) {
                boolean exists = existingRoles.stream()
                        .anyMatch(existingRole -> existingRole.getRole().getRoleId() == roleId);

                if (!exists) {
                    Role role = roleServiceImpl.getRoleByRoleId_ReturnRole(roleId);
                    if (role != null) {
                        UserRole userRole = new UserRole();
                        userRole.setRole(role);
                        userRole.setUser(user);
                        existingRoles.add(userRole); // Thêm vai trò mới
                    }
                }
            }

            // Cập nhật danh sách vai trò
            user.setUserRoleList(existingRoles);

            // Gọi save để cập nhật thông tin người dùng
            userRepository.save(user);

            // Lưu lại tất cả UserRole
            for (UserRole userRole : existingRoles) {
                userRoleRepository.save(userRole);
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
    @Override
    public boolean existsUserByUsername(String userName) {
        return userRepository.existsUserByUsername(userName);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

}
