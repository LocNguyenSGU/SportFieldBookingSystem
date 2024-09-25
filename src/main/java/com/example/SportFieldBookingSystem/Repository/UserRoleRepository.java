package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findUserRoleByUser_UserId(int userId);
}
