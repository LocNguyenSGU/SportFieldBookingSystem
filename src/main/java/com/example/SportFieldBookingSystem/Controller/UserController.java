package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách người dùng
        List<UserBasicDTO> userResponseDTOList = userServiceImpl.findAllUsersWithRoles();

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
    @GetMapping("/{userCode}")
    public ResponseEntity<?> getUserByUserCode(@PathVariable String userCode) {
        ResponseData responseData = new ResponseData();

        UserBasicDTO userResponseDTO = userServiceImpl.findUserWithRolesByUserCode(userCode);

        if (userResponseDTO == null) {
            responseData.setMessage("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        responseData.setData(userResponseDTO);
        responseData.setMessage("Successfully retrieved user.");
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/update/{userCode}")
    public ResponseEntity<?> updateUser(@PathVariable String userCode, @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            userServiceImpl.updateUser(userCode, userUpdateDTO);
            return ResponseEntity.ok("User updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating user: " + e.getMessage());
        }
    }




}
