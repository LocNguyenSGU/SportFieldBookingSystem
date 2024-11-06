package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import com.example.SportFieldBookingSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{userCode}")
    public ResponseEntity<?> getUserByUserCode(@PathVariable String userCode) {
        ResponseData responseData = new ResponseData();

        UserBasicDTO userResponseDTO = userService.findUserWithRolesByUserCode(userCode);

        if (userResponseDTO == null) {
            responseData.setMessage("User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        responseData.setData(userResponseDTO);
        responseData.setMessage("Successfully retrieved user.");
        return ResponseEntity.ok(responseData);
    }

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
    @GetMapping("/update/{userCode}")
    public ResponseEntity<?> updateUser(@PathVariable String userCode, @RequestBody UserUpdateDTO userUpdateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            userService.updateUser(userCode, userUpdateDTO);
            responseData.setMessage("User updated successfully.");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setMessage("Error updating user: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            responseData.setMessage("User created successfully.");
            userService.createUser(userCreateDTO);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setMessage("Error creating user: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseData> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String roleName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ResponseData responseData = new ResponseData();
        try {
            // Call service with search parameters and pagination details
            Page<UserBasicDTO> userBasicDTOPage = userService.getByUsernamePhoneAndRole(username, phone, roleName, page, size);

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



}
