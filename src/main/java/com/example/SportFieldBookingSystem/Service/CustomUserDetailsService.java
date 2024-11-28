package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserWithRolesByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
            User user = userOptional.get();
            System.out.println("Email luc kiem tra usser: " + user.getEmail());
        List<GrantedAuthority> authorityList = user.getUserRoleList().stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getRoleName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getFullName(),
                authorityList
        );
    }

    public boolean hasRole(String email, String roleName) {
        Optional<User> userOptional = userRepository.findUserWithRolesByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        User user = userOptional.get();
        return user.getUserRoleList().stream()
                .anyMatch(userRole -> userRole.getRole().getRoleName().equals(roleName));
    }

}
