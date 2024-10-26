package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.AuthDTO.LoginDTO;
import com.example.SportFieldBookingSystem.DTO.AuthDTO.LogoutDTO;
import com.example.SportFieldBookingSystem.DTO.AuthDTO.SignupDTO;
import com.example.SportFieldBookingSystem.DTO.InvalidToken.InvalidTokenDTO;
import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Security.JwtToken;
import com.example.SportFieldBookingSystem.Service.*;
import com.example.SportFieldBookingSystem.Service.Impl.LoginServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private InvalidTokenService invalidTokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login( @RequestBody LoginDTO loginDTO) {
        ResponseData responseData = new ResponseData();
        try {
            if (loginService.checkLogin(loginDTO.getUsername(), loginDTO.getPassword())) {
                String refreshToken = jwtToken.generateRefreshToken(loginDTO.getUsername());
                String accessToken = jwtToken.generateToken(loginDTO.getUsername());
                ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)// Chỉ dùng Http, không thể truy cập từ JavaScript
                        .secure(true)  // Chỉ gửi cookie qua HTTPS
                        .path("/")
                        .maxAge(7 * 24 * 60 * 60)
                        .build();
                responseData.setStatus(200);
                responseData.setMessage("Login success");
                responseData.setData(accessToken);
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                        .body(responseData);
            }
        } catch (UsernameNotFoundException e) {
            responseData.setStatus(404);
            responseData.setMessage("Login failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException e) {
            responseData.setStatus(401);
            responseData.setMessage("Login failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
        responseData.setStatus(500);
        responseData.setMessage("Login failed: Unknown error");
        return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignupDTO signupDTO) {
        ResponseData responseData = new ResponseData();
        Map<String, String> errorMap = new HashMap<>(); // Lưu lỗi theo cặp field - message
        boolean hasErrors = false; // Cờ kiểm tra lỗi

        // Kiểm tra email đã tồn tại chưa
        if (userService.existsUserByEmail(signupDTO.getEmail())) {
            errorMap.put("email", "Email has already been used");
            hasErrors = true;
        }

        // Kiểm tra username đã tồn tại chưa
        if (userService.existsUserByUsername(signupDTO.getUsername())) {
            errorMap.put("username", "Username has already been used");
            hasErrors = true;
        }

        // Kiểm tra mật khẩu khớp hay không
        if (!signupDTO.getRePassword().equals(signupDTO.getPassword())) {
            errorMap.put("password", "Passwords do not match");
            hasErrors = true;
        }

        // Nếu có lỗi, trả về danh sách lỗi
        if (hasErrors) {
            responseData.setStatus(400);
            responseData.setMessage("Validation failed");
            responseData.setData(errorMap); // Trả danh sách lỗi
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST); // 400
        }

        // Nếu không có lỗi, tiến hành đăng ký
        try {
            responseData.setStatus(200);
            userService.createUserSignUp(signupDTO);
            responseData.setMessage("Registration successful");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.setStatus(500);
            System.out.println("Error during signup: " + e);
            responseData.setMessage("An error occurred while processing your request.");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @RequestBody LogoutDTO logoutDTO) {
        ResponseData responseData = new ResponseData();
        try {
            String idToken = jwtToken.getIdTokenFromExpiredJwtToken(logoutDTO.getToken());
            Date expirationTime = jwtToken.getExpirationTimeFromExpiredJwtToken(logoutDTO.getToken());
            InvalidTokenDTO invalidTokenDTO = new InvalidTokenDTO(idToken, expirationTime);
            invalidTokenService.saveInvalidTokenIntoDatabase(invalidTokenDTO);

            String refreshToken = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("refreshToken")) {
                        refreshToken = cookie.getValue();
                        System.out.println("refreshToken: " + refreshToken);
                    }
                }
            }
            if(refreshToken != "")  {
                invalidTokenService.saveInvalidTokenIntoDatabase(new InvalidTokenDTO(jwtToken.getIdTokenFromExpiredJwtToken(refreshToken),jwtToken.getExpirationTimeFromExpiredJwtToken(refreshToken)));
            }

            ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", null)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(0) // Xóa cookie
                    .build();

            // Lấy refresh token từ cookie
            responseData.setStatus(200);
            responseData.setMessage("dang xuat thanh cong");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatus(500);
            responseData.setMessage("dang xuat that bai" + e);
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = null;
        ResponseData responseData = new ResponseData();
        // Lấy refresh token từ cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        // Kiểm tra refresh token có hợp lệ không
        if (refreshToken != null && jwtToken.validateJwtToken(refreshToken) && !invalidTokenService.existsByIdToken(refreshToken)) {
            String username = jwtToken.getUsernameFromJwtToken(refreshToken);
            String newAccessToken = jwtToken.generateToken(username);
            responseData.setStatus(200);
            responseData.setMessage("tao moi access token thanh cong");
            responseData.setData(newAccessToken);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setStatus(400);
            responseData.setMessage("Refresh token is invalid or expired");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }


}
