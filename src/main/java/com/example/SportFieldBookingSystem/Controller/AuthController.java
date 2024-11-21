package com.example.SportFieldBookingSystem.Controller;

import com.example.SportFieldBookingSystem.DTO.AuthDTO.*;
import com.example.SportFieldBookingSystem.DTO.InvalidToken.InvalidTokenDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserCreateDTO;
import com.example.SportFieldBookingSystem.DTO.UserDTO.UserUpdateDTO;
import com.example.SportFieldBookingSystem.Entity.Email;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import com.example.SportFieldBookingSystem.Payload.ResponseData;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Security.JwtToken;
import com.example.SportFieldBookingSystem.Service.*;
import com.example.SportFieldBookingSystem.Service.Impl.LoginServiceImpl;
import com.example.SportFieldBookingSystem.Service.Impl.UserServiceImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


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
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestBody LoginDTO loginDTO) {
        ResponseData responseData = new ResponseData();
        try {
            if (loginService.checkLogin(loginDTO.getEmail(), loginDTO.getPassword())) {
                String refreshToken = jwtToken.generateRefreshToken(loginDTO.getEmail());
                String accessToken = jwtToken.generateToken(loginDTO.getEmail());
                ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)// Chỉ dùng Http, không thể truy cập từ JavaScript
                        .secure(true)  // Chỉ gửi cookie qua HTTPS
                        .path("/")
                        .maxAge(7 * 24 * 60 * 60)
                        .build();
                responseData.setStatusCode(200);
                responseData.setMessage("Login success");
                responseData.setData(accessToken);
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                        .body(responseData);
            }
        } catch (UsernameNotFoundException e) {
            responseData.setStatusCode(404);
            responseData.setMessage("Login failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException e) {
            responseData.setStatusCode(401);
            responseData.setMessage("Login failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
        responseData.setStatusCode(500);
        responseData.setMessage("Login failed: Unknown error");
        return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/google/{accessToken}")
    public ResponseEntity<ResponseData> googleLogin(@PathVariable String accessToken) {
        ResponseData responseData = new ResponseData();

        try {
            // Gửi yêu cầu để lấy thông tin người dùng từ Google API
            String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> userInfo = restTemplate.getForObject(url, HashMap.class);

            if (userInfo == null || !userInfo.containsKey("email")) {
                responseData.setStatusCode(401);
                responseData.setMessage("Invalid Google Access Token");
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
            }

            // Lấy email và thông tin người dùng từ phản hồi
            String email = (String) userInfo.get("email");
            String fullName = (String) userInfo.get("name");


            // Kiểm tra người dùng đã tồn tại hay chưa
            Boolean existsUserByEmail = userService.existsUserByEmail(email);
            User user = new User();
            if (!existsUserByEmail) {
                user.setEmail(email);
                user.setFullName(fullName);
                user.setPassword(passwordEncoder.encode("123456"));
                userRepository.save(user);
            }

            // Tạo JWT token
            String myAccessToken = jwtToken.generateToken(email);
            String refreshToken = jwtToken.generateRefreshToken(email);

            // Set refresh token vào cookie
            ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();

            responseData.setStatusCode(200);

            responseData.setMessage("Google Login Success");
            responseData.setData(myAccessToken);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(responseData);
        } catch (Exception e) {
            responseData.setStatusCode(500);
            responseData.setMessage("Google Login Failed: " + e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
            responseData.setStatusCode(400);
            responseData.setMessage("Validation failed");
            responseData.setData(errorMap); // Trả danh sách lỗi
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST); // 400
        }

        // Nếu không có lỗi, tiến hành đăng ký
        try {
            responseData.setStatusCode(200);
            userService.createUserSignUp(signupDTO);
            responseData.setMessage("Registration successful");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (Exception e) {
            responseData.setStatusCode(500);
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
            responseData.setStatusCode(200);
            responseData.setMessage("dang xuat thanh cong");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setStatusCode(500);
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
            responseData.setStatusCode(200);
            responseData.setMessage("tao moi access token thanh cong");
            responseData.setData(newAccessToken);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setStatusCode(400);
            responseData.setMessage("Refresh token is invalid or expired");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        ResponseData responseData = new ResponseData();
        String email = forgotPasswordDTO.getEmail();
        UserBasicDTO userBasicDTO = userService.getUserByEmail(email);
        if (userBasicDTO == null) {
            responseData.setStatusCode(404);
            responseData.setMessage("tai khoan khong co voi " + email);
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        String refreshTokenPassword = userService.createPasswordResetToken(email);
        String resetLink = "http://localhost:5173/reset_password?token=" + refreshTokenPassword;
        Email emailSend = new Email();
        emailSend.setToEmail(email);
        emailSend.setSubject("Reset Your Password");
        emailService.sendHtmlEMail(emailSend, resetLink, userBasicDTO.getUsername());
        responseData.setStatusCode(200);
        responseData.setMessage("Reset password link has been sent to your email");
        responseData.setData("");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {

        ResponseData responseData = new ResponseData();
        String refreshPasswordToken = resetPasswordDTO.getRefreshPasswordToken();
        if (!isValidJwtFormat(refreshPasswordToken)) {
            responseData.setStatusCode(400);
            responseData.setMessage("Token không hợp lệ");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        String idRefreshPasswordToken = jwtToken.getIdTokenFromExpiredJwtToken(refreshPasswordToken);

        // Kiểm tra token có hợp lệ không
        if (refreshPasswordToken == null || !jwtToken.validateJwtToken(refreshPasswordToken) || invalidTokenService.existsByIdToken(refreshPasswordToken)) {
            responseData.setStatusCode(400);
            responseData.setMessage("Token không hợp lệ");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Lấy email từ token
        String email = jwtToken.getUsernameFromJwtToken(refreshPasswordToken);

        // Lấy thời gian hết hạn của token và chuyển từ Date sang LocalDateTime
        Date expirationDate = jwtToken.getExpirationTimeTokenFromJwtToken(refreshPasswordToken);
        LocalDateTime expirationTime = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // So sánh thời gian hết hạn với thời gian hiện tại
        if (expirationTime.isBefore(LocalDateTime.now())) {
            responseData.setStatusCode(400);
            responseData.setMessage("Token đã hết hạn");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // Tìm tài khoản dựa trên email
        Optional<User> userOptional = userService.getUserEntityByEmail(email);
        if (userOptional.isEmpty()) {
            responseData.setStatusCode(404);
            responseData.setMessage("Không tìm thấy người dùng");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        // Lấy tài khoản và cập nhật mật khẩu mới
        User user = userOptional.get();
        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getReNewPassword())) {
            responseData.setStatusCode(400);
            responseData.setMessage("Mật khẩu nhập lại không khớp");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }
        user.setPassword(resetPasswordDTO.getNewPassword());
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setPassword(user.getPassword());
        userUpdateDTO.setEmail(user.getEmail());

        // Cập nhật tài khoản với mật khẩu mới
        boolean isSuccess = userService.updateUser(userUpdateDTO);

        // Loại bỏ token
        if (isSuccess) {
            InvalidTokenDTO invalidTokenDTO = new InvalidTokenDTO(idRefreshPasswordToken, expirationDate);
            invalidTokenService.saveInvalidTokenIntoDatabase(invalidTokenDTO);
            responseData.setStatusCode(200);
            responseData.setMessage("Đặt lại mật khẩu thành công");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setStatusCode(500);
            responseData.setMessage("Đặt lại mật khẩu thất bại");
            responseData.setData("");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getRefreshTokenFromRequest(HttpServletRequest httpServletRequest) {
        String refreshToken = null;

        // Lấy refresh token từ cookie
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    System.out.println("refreshToken: " + refreshToken);
                }
            }
        }
        return refreshToken;
    }

    private boolean isValidJwtFormat(String token) {
        // Kiểm tra token không null và có đúng định dạng JWT
        return token != null && token.split("\\.").length == 3;
    }






}
