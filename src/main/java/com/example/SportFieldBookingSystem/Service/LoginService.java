package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Repository.UserRepository;
import com.example.SportFieldBookingSystem.Service.Impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements LoginServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean checkLogin(String username, String password) {
        Optional<User> userOptional = userRepository.findUserWithRolesByUsername(username);
        if(userOptional.isEmpty()) {
            System.out.println("Khoong lay duoc user thong qua username");
            return false;
        }
        User user = userOptional.get();
        System.out.println("Da lay duoc user thong qua username");
        return passwordEncoder.matches(password, user.getPassword());
    }
}
