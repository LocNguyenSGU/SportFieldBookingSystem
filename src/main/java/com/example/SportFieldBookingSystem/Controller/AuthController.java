package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.LoginDTO.LoginDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Security.JwtToken;
import com.example.SportFieldBookingSystem.Service.Impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private JwtToken jwtToken;
    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestBody LoginDTO loginDTO) {
        ResponseData responseData = new ResponseData();

        // Kiểm tra đăng nhập
        if (loginServiceImpl.checkLogin(loginDTO.getUsername(), loginDTO.getPassword())) {
            responseData.setMessage("Login success");
            responseData.setData(jwtToken.generateToken(loginDTO.getUsername()));

            // Trả về 200 OK nếu đăng nhập thành công
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setMessage("Login failed");
            responseData.setData("");

            // Trả về 401 UNAUTHORIZED nếu đăng nhập thất bại
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
    }
}
