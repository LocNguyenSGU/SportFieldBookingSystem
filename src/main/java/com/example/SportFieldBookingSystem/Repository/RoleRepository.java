package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleId(int roleId);
    boolean existsByRoleName(String roleName);


}
