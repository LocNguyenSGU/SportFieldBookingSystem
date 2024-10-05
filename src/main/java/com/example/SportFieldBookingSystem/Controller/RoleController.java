package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleByUserDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            responseData.setMessage("Role created successfully.");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.setMessage("Error create role with permission: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/user/{userName}")
    public ResponseEntity<?> getRoleByUserName(@PathVariable String userName) {
        ResponseData responseData = new ResponseData();
        try {
            RoleByUserDTO roleByUserDTO = roleService.getListRoleByUserRoleList_User_UserName(userName);
            if(roleByUserDTO == null) {
                responseData.setMessage("khong lay duoc role theo userName");
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
            responseData.setData(roleByUserDTO);
            responseData.setMessage("Lay role name theo userName");
            return new  ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setMessage("Error get role by userName: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateRoleWithPermission(@RequestBody RoleUpdateDTO roleUpdateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            roleService.updateRoleWithPermission(roleUpdateDTO);
            responseData.setMessage("Role updated successfully.");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setMessage("Error update role with permission: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }


}
