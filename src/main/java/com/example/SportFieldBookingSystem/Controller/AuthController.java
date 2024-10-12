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
                ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)// Chỉ dùng Http, không thể truy cập từ JavaScript
                        .secure(true)  // Chỉ gửi cookie qua HTTPS
                        .path("http://localhost:8080/auth/refresh_token")// Đường dẫn của API refresh token
                        .maxAge(7 * 24 * 60 * 60)
                        .build();

                responseData.setMessage("Login success");
                responseData.setData(jwtToken.generateToken(loginDTO.getUsername()));
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                        .body(responseData);
            }
        } catch (UsernameNotFoundException e) {
            responseData.setMessage("Login failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException e) {
            responseData.setMessage("Login failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }

        responseData.setMessage("Login failed: Unknown error");
        return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignupDTO signupDTO) {
        ResponseData responseData = new ResponseData();
        Map<String, String> errorMap = new HashMap<>(); // Dùng Map để lưu lỗi theo cặp field - message
        boolean hasErrors = false; // Cờ để kiểm tra nếu có lỗi

        // Kiểm tra xem email đã tồn tại hay chưa
        if (userService.existsUserByEmail(signupDTO.getEmail())) {
            errorMap.put("email", "Email has already been used");
            hasErrors = true; // Bật cờ lỗi
        }

        // Kiểm tra xem username đã tồn tại hay chưa
        if (userService.existsUserByUsername(signupDTO.getUsername())) {
            errorMap.put("username", "Username has already been used");
            hasErrors = true; // Bật cờ lỗi
        }

        // Kiểm tra xem mật khẩu có khớp hay không
        if (!signupDTO.getRePassword().equals(signupDTO.getPassword())) {
            errorMap.put("password", "Passwords do not match");
            hasErrors = true; // Bật cờ lỗi
        }

        // Nếu có bất kỳ lỗi nào thì trả về danh sách lỗi
        if (hasErrors) {
            responseData.setMessage("Validation failed");
            responseData.setData(errorMap); // Gửi danh sách lỗi dưới dạng Map
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Không có lỗi thì tiến hành đăng ký người dùng
        try {
            userService.createUserSignUp(signupDTO);
            responseData.setMessage("Registration successful");
        } catch (Exception e) {
            System.out.println(e);
            responseData.setMessage("An error occurred while processing your request.");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutDTO logoutDTO) {
        ResponseData responseData = new ResponseData();
        try {
            String idToken = jwtToken.getIdTokenFromJwtToken(logoutDTO.getToken());
            String userName = jwtToken.getUsernameFromJwtToken(logoutDTO.getToken());
            Date expirationTime = jwtToken.getExpirationTimeTokenFromJwtToken(logoutDTO.getToken());
            InvalidTokenDTO invalidTokenDTO = new InvalidTokenDTO(idToken, expirationTime);
            invalidTokenService.saveInvalidTokenIntoDatabase(invalidTokenDTO);

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
        if (refreshToken != null && jwtToken.validateJwtToken(refreshToken)) {
            String username = jwtToken.getUsernameFromJwtToken(refreshToken);
            String newAccessToken = jwtToken.generateToken(username);
            responseData.setStatus(200);
            responseData.setMessage("tao moi access token thanh cong");
            responseData.setData(newAccessToken);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setStatus(401);
            responseData.setMessage("Refresh token is invalid or expired");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }


}
