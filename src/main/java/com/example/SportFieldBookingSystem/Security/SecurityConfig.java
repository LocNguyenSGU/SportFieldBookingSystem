package com.example.SportFieldBookingSystem.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF nếu không cần thiết
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()) // Cho phép tất cả các yêu cầu
                .cors(cors -> {
                    // Đảm bảo rằng CORS được cấu hình qua WebConfig
                });

        return httpSecurity.build();
    }
}
