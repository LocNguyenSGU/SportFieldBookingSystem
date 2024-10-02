package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleByUserDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleCreateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
            return ResponseEntity.ok("User created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error create role with permission: " + e.getMessage());
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error get role by userName: " + e.getMessage());
        }
    }


}
