package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.PermissionDTO.PermissionDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@RequestMapping("/permission")
@Controller
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public ResponseEntity<?> getAllPermission(){
        ResponseData responseData = new ResponseData();

        // Lấy danh sách đánh giá
        List<PermissionDTO> chucNangDTOList = permissionService.getAllPermission();

        // Kiểm tra nếu danh sách đánh giá trống
        if (chucNangDTOList == null) {
            responseData.setStatusCode(404);
            responseData.setMessage("chuc nang not found.");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
        responseData.setStatusCode(200);
        responseData.setData(chucNangDTOList);
        responseData.setMessage("Successfully retrieved all chucNang.");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
