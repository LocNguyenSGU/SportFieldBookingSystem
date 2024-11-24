package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleByUserDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleCreateDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleResponseDTO;
import com.example.SportFieldBookingSystem.DTO.RoleDTO.RoleUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.Role;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @PostMapping("/create")
    public ResponseEntity<?> createRoleWithPermission(@Valid @RequestBody RoleCreateDTO roleCreateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            if(roleService.existsByRoleName(roleCreateDTO.getRoleName())) {
                responseData.setStatusCode(400);
                responseData.setMessage("Role name has been taken");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
            roleService.createRoleWithPermission(roleCreateDTO);
            responseData.setStatusCode(201);
            responseData.setMessage("Role created successfully.");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Error create role with permission: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/user/{email}")
    public ResponseEntity<?> getRoleByEmail(@PathVariable String email) {
        ResponseData responseData = new ResponseData();
        try {
            RoleByUserDTO roleByUserDTO = roleService.getListRoleByUserRoleList_User_Email(email);
            if(roleByUserDTO == null) {
                responseData.setMessage("khong lay duoc role theo email");
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
            responseData.setData(roleByUserDTO);
            responseData.setMessage("Lay role name theo email");
            return new  ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setMessage("Error get role by email: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllRole() {
        ResponseData responseData = new ResponseData();
        try {
            List<RoleResponseDTO> roleResponseDTOList = roleService.getAllRole();

            if (roleResponseDTOList.isEmpty()) {
                responseData.setStatusCode(404);
                responseData.setMessage("No roles found");
                responseData.setData(null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }

            responseData.setStatusCode(200);
            responseData.setMessage("Fetched all roles successfully");
            responseData.setData(roleResponseDTOList);
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch (Exception e) {
            responseData.setStatusCode(400);
            responseData.setMessage("Error fetching roles: " + e.getMessage());
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchQuyenByName(@RequestParam String name,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        ResponseData responseData = new ResponseData();

        // Lấy danh sách quyen theo tên
        Page<RoleResponseDTO> quyenResponseDTOPage = roleService.searchRoleByRoleName(name, page, size);

        // Kiểm tra nếu danh sách quyen trống
        if (quyenResponseDTOPage.isEmpty()) {
            responseData.setStatusCode(204);
            responseData.setMessage("No quyen found.");
            responseData.setData("");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseData);
        }

        // Nếu có dữ liệu
        responseData.setStatusCode(200);
        responseData.setData(quyenResponseDTOPage);
        responseData.setMessage("Successfully retrieved quyen by name.");
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{idRole}")
    public ResponseEntity<ResponseData> getQuyenDetail(@PathVariable int idRole) {
        ResponseData responseData = new ResponseData();
        try {
            RoleResponseDTO quyenResponseDTO = roleService.getRoleDetailById(idRole);

            // Kiểm tra đăng nhập
            if (quyenResponseDTO != null) {
                responseData.setStatusCode(200);
                responseData.setMessage("Lấy thông tin chi tiết quyền thành công");
                responseData.setData(quyenResponseDTO);
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setStatusCode(204);
                responseData.setMessage("Không tìm thấy quyền với ID: " + idRole);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Có lỗi xảy ra: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateRoleWithPermission(@Valid @RequestBody RoleUpdateDTO roleUpdateDTO) {
        ResponseData responseData = new ResponseData();
        try {
            if(roleService.existsByRoleNameAndNotRoleIdNot(roleUpdateDTO.getRoleName(), roleUpdateDTO.getRoleId())) {
                responseData.setStatusCode(234);
                responseData.setMessage("Cập nhật quyền thất bại: Tên quyền đã tồn tại");
                responseData.setData("");
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }

            roleService.updateRoleWithPermission(roleUpdateDTO);
            responseData.setStatusCode(200);
            responseData.setMessage("Role updated successfully.");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Error update role with permission: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }

}
