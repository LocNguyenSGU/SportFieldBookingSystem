package com.example.SportFieldBookingSystem.Security;

import com.example.SportFieldBookingSystem.Service.CustomUserDetailsService;
import com.example.SportFieldBookingSystem.Service.InvalidTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtToken {

    @Value("${jwt.secret}")
    private String secretKey;
    @Autowired
    private InvalidTokenService invalidTokenService;
    private final long JWT_EXPIRATION = 15 * 1000L; // 15
    private final long JWT_REFRESH_EXPIRATION = 45 * 1000L; // 45 s

    // Tạo JWT từ username
    public String generateToken(String data) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // Tạo ID cho token
        String tokenId = UUID.randomUUID().toString(); // Tạo ID ngẫu nhiên
        String jws = Jwts.builder()
                .setSubject(data)
                .setIssuedAt(new Date())
                .setId(tokenId)
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION)) // Thời gian hết hạn
                .signWith(key)
                .compact();
        return jws;
    }

    public String generateRefreshToken(String data) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // Tạo ID cho token
        String tokenId = UUID.randomUUID().toString(); // Tạo ID ngẫu nhiên
        String jws = Jwts.builder()
                .setSubject(data)
                .setIssuedAt(new Date())
                .setId(tokenId)
                .setExpiration(new Date((new Date()).getTime() + JWT_REFRESH_EXPIRATION)) // Thời gian hết hạn
                .signWith(key)
                .compact();
        return jws;
    }

    // Lấy username từ JWT
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getIdTokenFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getId();
    }
    public Date getExpirationTimeTokenFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    public String getIdTokenFromExpiredJwtToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Secret key để ký token
                    .build()
                    .parseClaimsJws(token)     // Parse JWT nhưng bỏ qua expiration
                    .getBody();
            return claims.getId(); // Trả về ID từ token

        } catch (ExpiredJwtException expiredException) {
            // Token hết hạn nhưng vẫn giải mã được phần Claims
            System.out.println("Token hết hạn, nhưng vẫn lấy được dữ liệu");
            return expiredException.getClaims().getId(); // Lấy thông tin từ phần Claims của token đã hết hạn

        } catch (SignatureException e) {
            // Token không hợp lệ về mặt chữ ký
            System.out.println("Token không hợp lệ: " + e.getMessage());
            return null;
        }
    }

    public Date getExpirationTimeFromExpiredJwtToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Secret key để ký token
                    .build()
                    .parseClaimsJws(token)     // Parse JWT nhưng bỏ qua expiration
                    .getBody();

            return claims.getExpiration();

        } catch (ExpiredJwtException expiredException) {
            // Token hết hạn nhưng vẫn giải mã được phần Claims
            System.out.println("Token hết hạn, nhưng vẫn lấy được dữ liệu");
            return expiredException.getClaims().getExpiration(); // Lấy thông tin từ phần Claims của token đã hết hạn

        } catch (SignatureException e) {
            // Token không hợp lệ về mặt chữ ký
            System.out.println("Token không hợp lệ: " + e.getMessage());
            return null;
        }
    }

    // Xác thực JWT
    public boolean validateJwtToken(String token) {
        try {
            String tokenId = getIdTokenFromJwtToken(token);
            if(invalidTokenService.existsByIdToken(tokenId)) {
                return false; // token nay da bi thu hoi. (dang duoc luu trong db)
            }
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
}