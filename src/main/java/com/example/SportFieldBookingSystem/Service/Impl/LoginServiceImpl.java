package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean checkLogin(String username, String password) {
        Optional<User> userOptional = userRepository.findUserWithRolesByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return true;
    }

}
