package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import com.example.SportFieldBookingSystem.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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



}
