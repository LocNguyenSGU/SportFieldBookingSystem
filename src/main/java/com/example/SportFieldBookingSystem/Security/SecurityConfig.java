package com.example.SportFieldBookingSystem.Security;

import com.example.SportFieldBookingSystem.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CustomPermissionEvaluator permissionEvaluator;
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((request) -> request
//                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/role/create")
                        .access((authenticationSupplier, context) -> checkPermission(authenticationSupplier, "Quản lí nhóm quyền", "CREATE"))
                        .requestMatchers("/role/update")
                        .access((authenticationSupplier, context) -> checkPermission(authenticationSupplier, "Quản lí nhóm quyền", "EDIT"))
                        .requestMatchers("/user/update/**")
                        .access((authenticationSupplier, context) -> checkPermission(authenticationSupplier, "Quản lí người dùng", "EDIT"))
                        .requestMatchers("/user/username/**")
                        .access((authenticationSupplier, context) -> checkPermission(authenticationSupplier, "Quản lí người dùng", "VIEW"))
                        .requestMatchers(HttpMethod.PUT, "/api/fields/{id}")
                        .access((authenticationSupplier, context) -> checkPermission(authenticationSupplier, "Quản lí sân", "EDIT"))
                        .requestMatchers(HttpMethod.POST, "/api/fields")
                        .access((authenticationSupplier, context) -> checkPermission(authenticationSupplier, "Quản lí sân", "CREATE"))
                        .requestMatchers(HttpMethod.PUT, "/api/fieldType{id}")
                        .access((authenticationSupplier, context) -> checkPermission(authenticationSupplier, "Quản lí loại sân", "EDIT"))
                        .requestMatchers(HttpMethod.POST,"/api/fieldType")
                        .access((authenticationSupplier, context) -> checkPermission(authenticationSupplier, "Quản lí loại sân", "CREATE"))
                        .requestMatchers(HttpMethod.POST, "/user/create")
                        .access((authenticationSupplier, context) -> checkPermission(authenticationSupplier, "Quản lí người dùng", "CREATE"))
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        http.sessionManagement(session ->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Phương thức kiểm tra quyền chung
    private AuthorizationDecision checkPermission(Supplier<Authentication> authenticationSupplier, String targetDomainObject, String permission) {
        Authentication authentication = authenticationSupplier.get(); // Lấy Authentication từ Supplier
        boolean hasPermission = permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);
        return new AuthorizationDecision(hasPermission);
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Địa chỉ front-end
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager customAuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new AuthenticatedVoter());

        return new UnanimousBased(decisionVoters);
    }
}
