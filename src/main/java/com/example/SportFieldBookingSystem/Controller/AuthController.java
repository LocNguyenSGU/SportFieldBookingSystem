package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.AuthDTO.LoginDTO;
import com.example.SportFieldBookingSystem.DTO.AuthDTO.SignupDTO;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Security.JwtToken;
import com.example.SportFieldBookingSystem.Service.CustomUserDetailsService;
import com.example.SportFieldBookingSystem.Service.Impl.LoginServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestBody LoginDTO loginDTO) {
        ResponseData responseData = new ResponseData();
        try {
            // Kiểm tra đăng nhập
            if (loginServiceImpl.checkLogin(loginDTO.getUsername(), loginDTO.getPassword())) {
                responseData.setMessage("Login success");
                responseData.setData(jwtToken.generateToken(loginDTO.getUsername()));
                // Trả về 200 OK nếu đăng nhập thành công
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
        } catch (UsernameNotFoundException e) {
            responseData.setMessage("Login failed: " + e.getMessage());
            responseData.setData("");
            // Trả về 404 nếu username không tồn tại
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException e) {
            responseData.setMessage("Login failed: " + e.getMessage());
            responseData.setData("");
            // Trả về 401 nếu mật khẩu sai
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
        // Dự phòng nếu có lỗi khác xảy ra
        responseData.setMessage("Login failed: Unknown error");
        responseData.setData("");
        return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignupDTO signupDTO) {
        ResponseData responseData = new ResponseData();
        try {
            if (userServiceImpl.existsUserByEmail(signupDTO.getEmail())) {
                responseData.setMessage("Email has already been used");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
            if (userServiceImpl.existsUserByUsername(signupDTO.getUsername())) {
                responseData.setMessage("Username has already been used");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
            if(!signupDTO.getRePassword().equals(signupDTO.getPassword())) {
                responseData.setMessage("Passwords do not match");
                return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
            }
            userServiceImpl.createUserSignUp(signupDTO);
            responseData.setMessage("Registration successful");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


}
