package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/create")
    public ResponseEntity<?> createRoleWithPermission(@RequestBody RoleCreateDTO roleCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            if(roleService.existsByRoleName(roleCreateDTO.getRoleName())) {
                responseData.setMessage("Role name has been taken");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
            roleService.createRoleWithPermission(roleCreateDTO);
            return ResponseEntity.ok("User created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error create role with permission: " + e.getMessage());
        }
    }
}
