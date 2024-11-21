package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.DTO.AuthDTO.SignupDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserGetDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import com.example.SportFieldBookingSystem.Enum.UserEnum;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Mapper.UserMapper;
import com.example.SportFieldBookingSystem.Security.JwtToken;
import com.example.SportFieldBookingSystem.Service.RoleService;
import com.example.SportFieldBookingSystem.Service.UserRoleService;
import com.example.SportFieldBookingSystem.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private UserMapper userMapper = new UserMapper();
    @Autowired
    @Lazy
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private ModelMapper modelMapper;


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
    public UserBasicDTO findUserWithRolesByUserName(String username) {
        try {
            // Tìm người dùng dựa trên userCode
            Optional<User> userOptional = userRepository.findUserWithRolesByUsername(username);

            // Kiểm tra xem người dùng có tồn tại hay không
            if (userOptional.isPresent()) {
                User user = userOptional.get(); // Lấy người dùng
                return userMapper.toBasicDTO(user); // Chuyển đổi sang DTO và trả về
            } else {
                // Xử lý khi không tìm thấy người dùng
                System.out.println("Không tìm thấy người dùng với userName: " + username);
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
    public Page<UserBasicDTO> findAllUsersWithRoles(int page, int size) {
        try {
            // Tạo Pageable với page và size
            Pageable pageable = PageRequest.of(page, size);

            // Lấy Page<User> từ repository
            Page<User> userPage = userRepository.findAllUsersWithRoles(pageable);

            // Ánh xạ Page<User> sang Page<UserBasicDTO> bằng cách sử dụng map
            return userPage.map(userMapper::toBasicDTO);
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error fetching all users: " + e.getMessage());
            // Optionally, throw a custom exception
            throw new RuntimeException("Unable to fetch users", e);
        }
    }

    @Override
    @Transactional
    public void updateUser(int userId, UserUpdateDTO userUpdateDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

//            if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().equals(user.getEmail())) {
//                if (existsUserByEmail(userUpdateDTO.getEmail())) {
//                    throw new RuntimeException("Email already exists.");
//                }
//                user.setEmail(userUpdateDTO.getEmail()); // Cập nhật email nếu hợp lệ
//            }
//
//
//            // Cập nhật thông tin người dùng
//            user.setEmail(userUpdateDTO.getEmail());
            user.setPhone(userUpdateDTO.getPhone());
            user.setFullName(userUpdateDTO.getFullName());

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
                    Role role = roleService.getRoleByRoleId_ReturnRole(roleId);
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
                userRoleService.saveUserRole(userRole);
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public boolean updateUser(UserUpdateDTO userUpdateDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userUpdateDTO.getEmail());
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void updateUserEntity(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsUserByUsername(String userName) {
        return userRepository.existsUserByUsername(userName);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public Optional<UserBasicDTO> getUserByUsername(String userName) {
        Optional<User> userOptional = userRepository.findByUsername(userName);
        return Optional.ofNullable(userMapper.toBasicDTO(userOptional.get()));
    }

    @Override
    public UserBasicDTO getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            return userMapper.toBasicDTO(userOptional.get());
        }
        return null;
    }

    @Override
    public Optional<User> getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean createUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        try {
            // Ánh xạ từ UserCreateDTO sang User
            user.setFullName(userCreateDTO.getFullName());
            user.setEmail(userCreateDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
            user.setPhone(userCreateDTO.getPhone());
            user.setThoiGianTao(LocalDateTime.now());

            // Thiết lập trạng thái
            if (userCreateDTO.getStatus() != null) {
                try {
                    user.setStatus(UserEnum.valueOf(userCreateDTO.getStatus().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid status value: " + userCreateDTO.getStatus());
                }
            }
            List<Integer> roleIdList = userCreateDTO.getRoleIdList();
            User newUser = userRepository.save(user);
            Role newRole = new Role();
            for(Integer roleId : roleIdList) {
                newRole = roleService.getRoleByRoleId_ReturnRole(roleId);
            }
            UserRole userRole = new UserRole();
            userRole.setRole(newRole);
            userRole.setUser(newUser);
            userRoleService.saveUserRole(userRole);
            return true; // Trả về true nếu lưu thành công
        } catch (Exception e) {
            // Có thể log lỗi tại đây nếu cần thiết
            System.err.println("Error creating user: " + e.getMessage());
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    @Override
    public boolean createUserSignUp(SignupDTO signupDTO) {
        User user = new User();
        try {
            user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
            user.setEmail(signupDTO.getEmail());
            user.setStatus(UserEnum.valueOf(UserEnum.ACTIVE.name()));
            user.setThoiGianTao(LocalDateTime.now());
            User newUser = userRepository.save(user);

            Role role = roleService.getRoleByRoleId_ReturnRole(3); // role 3: khach hang
            UserRole userRole = new UserRole();
            userRole.setUser(newUser);
            userRole.setRole(role);
            userRoleService.saveUserRole(userRole);
            return true;
        }catch (Exception e) {
            System.out.println("Error creating user while signup: " + e.getMessage());
        }
        return false;
    }

    @Override
    public String createPasswordResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("tai khoang khong co voi " + email);
        }

        String tokenRefreshPassword = jwtToken.generateToken(email);
        User user = userOptional.get();
        user.setRefreshPasswordToken(tokenRefreshPassword);
        userRepository.save(user);
        return tokenRefreshPassword;
    }

    @Override
    public Page<UserBasicDTO> getByEmailPhoneAndRole(String email, String phone, String roleName, int page, int size) {
        System.out.println("email" +  email);
        System.out.println("roleName" +  roleName);

        Page<User> userPage = userRepository.findByEmailPhoneAndRole(email, phone, roleName, PageRequest.of(page, size));
        return userPage.map(userMapper::toBasicDTO);
    }

    @Override
    public UserBasicDTO getUserByUserId(int userId) {
        try {
            // Tìm người dùng dựa trên userCode
            Optional<User> userOptional = userRepository.findById(userId);

            // Kiểm tra xem người dùng có tồn tại hay không
            if (userOptional.isPresent()) {
                User user = userOptional.get(); // Lấy người dùng
                return userMapper.toBasicDTO(user); // Chuyển đổi sang DTO và trả về
            } else {
                // Xử lý khi không tìm thấy người dùng
                System.out.println("Không tìm thấy người dùng với id: " + userId);
                return null; // Hoặc ném ngoại lệ tùy theo yêu cầu
            }
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error fetching user: " + e.getMessage());
            // Ném ra một ngoại lệ tùy chọn
            throw new RuntimeException("Unable to fetch user", e);
        }
    }

}
