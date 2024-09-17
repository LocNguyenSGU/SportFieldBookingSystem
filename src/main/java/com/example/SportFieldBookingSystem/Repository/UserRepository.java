package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAll();
    public User findUserByUserCode(String userCode);
}
