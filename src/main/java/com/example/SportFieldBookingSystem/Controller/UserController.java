package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.UserDTO.ChangPasswordDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Security.JwtToken;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import com.example.SportFieldBookingSystem.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> getAllUser( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách người dùng
        Page<UserBasicDTO> userResponseDTOList = userService.findAllUsersWithRoles(page, size);

        // Kiểm tra nếu danh sách người dùng trống
        if (userResponseDTOList.isEmpty()) {
            responseData.setMessage("No users found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }

        // Nếu có dữ liệu
        responseData.setData(userResponseDTOList);
        responseData.setMessage("Successfully retrieved all users.");
        return ResponseEntity.ok(responseData);
    }

    // Thêm phương thức để lấy người dùng theo userCode
//    @GetMapping("/{userCode}")
//    public ResponseEntity<?> getUserByUserCode(@PathVariable String userCode) {
//        ResponseData responseData = new ResponseData();
//
//        UserBasicDTO userResponseDTO = userService.findUserWithRolesByUserCode(userCode);
//
//        if (userResponseDTO == null) {
//            responseData.setMessage("User not found.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
//        }
//
//        responseData.setData(userResponseDTO);
//        responseData.setMessage("Successfully retrieved user.");
//        return ResponseEntity.ok(responseData);
//    }

    @PostMapping("/username/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String username) {
        ResponseData responseData = new ResponseData();

        UserBasicDTO userResponseDTO = userService.findUserWithRolesByUserName(username);

        if (userResponseDTO == null) {
            responseData.setMessage("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        responseData.setData(userResponseDTO);
        responseData.setMessage("Successfully retrieved user.");
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            userService.updateUser(userId, userUpdateDTO);
            responseData.setStatusCode(200);
            responseData.setMessage("User updated successfully.");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatusCode(400);
            responseData.setData("");
            responseData.setMessage("Error updating user: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        ResponseData responseData = new ResponseData();
        Map<String, String> errorMap = new HashMap<>(); // Lưu lỗi theo cặp field - message
        boolean hasErrors = false; // Cờ kiểm tra lỗi

        // Kiểm tra email đã tồn tại chưa
        if (userService.existsUserByEmail(userCreateDTO.getEmail())) {
            errorMap.put("email", "Email has already been used");
            hasErrors = true;
        }

        // Kiểm tra mật khẩu khớp hay không
        if (!userCreateDTO.getRePassword().equals(userCreateDTO.getPassword())) {
            errorMap.put("password", "Passwords do not match");
            hasErrors = true;
        }

        // Nếu có lỗi, trả về danh sách lỗi
        if (hasErrors) {
            responseData.setStatusCode(400);
            responseData.setMessage("Validation failed");
            responseData.setData(errorMap); // Trả danh sách lỗi
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST); // 400
        }

        // Nếu không có lỗi, tiến hành đăng ký
        try {
            responseData.setStatusCode(200);
            userService.createUser(userCreateDTO);
            responseData.setMessage("Tao moi user thanh cong");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            System.out.println("Error during tao moi user: " + e);
            responseData.setMessage("An error occurred while processing your request.");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseData> searchUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String roleName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ResponseData responseData = new ResponseData();
        try {
            // Call service with search parameters and pagination details
            Page<UserBasicDTO> userBasicDTOPage = userService.getByEmailPhoneAndRole(email, phone, roleName, page, size);

            // Set up response data with results and success status
            responseData.setStatusCode(200);
            responseData.setMessage("Search successful");
            responseData.setData(userBasicDTOPage);
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch (Exception e) {
            // Handle exception and set error status/message
            responseData.setStatusCode(500);
            responseData.setMessage("Error occurred: " + e.getMessage());
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update_password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody ChangPasswordDTO changPasswordDTO) {
        ResponseData responseData = new ResponseData();
        Map<String, String> errorMap = new HashMap<>();
        boolean isError = false;

        // Lấy tên đăng nhập từ Authentication
        String accessToken = changPasswordDTO.getAccessToken();
        String email = "";
        if (jwtToken.validateJwtToken(accessToken)) {
            email = jwtToken.getUsernameFromJwtToken(accessToken);
        }

        // Kiểm tra mật khẩu cũ có chính xác không
       Optional<User> userOptional = userService.getUserEntityByEmail(email);
        if (userOptional.isEmpty()) {
            responseData.setStatusCode(204);
            responseData.setMessage("Người dùng không tồn tại.");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(changPasswordDTO.getMatKhauCu(), userOptional.get().getPassword())) {
            errorMap.put("matKhauCu", "Mật khẩu cũ không chính xác");
            isError = true;
        }

        // Kiểm tra mật khẩu mới và re_password có khớp không
        if (!changPasswordDTO.getMatKhau().equals(changPasswordDTO.getReMatKhau())) {
            errorMap.put("matKhauMoi", "Mật khẩu mới không khớp");
            isError = true;
        }

        // Nếu có lỗi
        if (isError) {
            responseData.setStatusCode(409);
            responseData.setMessage("Đổi mật khẩu không thành công");
            responseData.setData(errorMap); // Trả về errorMap chứa các lỗi
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(changPasswordDTO.getMatKhau()));
        userService.updateUserEntity(user);
        responseData.setStatusCode(200);
        responseData.setMessage("Đổi mật khẩu thành công!");
        responseData.setData("");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable int userId) {
        ResponseData responseData = new ResponseData();

        UserBasicDTO userResponseDTO = userService.getUserByUserId(userId);

        if (userResponseDTO == null) {
            responseData.setStatusCode(404);
            responseData.setMessage("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        responseData.setStatusCode(200);
        responseData.setData(userResponseDTO);
        responseData.setMessage("Successfully retrieved user.");
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        ResponseData responseData = new ResponseData();
        if(authentication == null) {
            responseData.setData("");
            responseData.setStatusCode(400);
            responseData.setMessage("Token het han roi, khong lay duoc thong tin");
            return new ResponseEntity<>(responseData, HttpStatus.FORBIDDEN);
        }
        String email = authentication.getName();
        System.out.println("Email: " + email);
        try {
            UserBasicDTO userBasicDTO = userService.getUserByEmail(email);
            if(userBasicDTO == null)
            {
                responseData.setStatusCode(404);
                responseData.setData("");
                responseData.setMessage("Tài khoản không tồn tại.");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                // Nếu tồn tại tài khoản
                responseData.setStatusCode(200);
                responseData.setMessage("Thông tin tài khoản");
                responseData.setData(userBasicDTO);
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            responseData.setStatusCode(404);
            responseData.setData("");
            responseData.setMessage("Tài khoản không tồn tại.");
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setData("");
            responseData.setMessage("Đã xảy ra lỗi khi lấy thông tin tài khoản: " + e);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getUserCount() {
        long userCount = userService.getUserCount();
        return ResponseEntity.ok().body(Map.of("userCount", userCount));
    }


}
