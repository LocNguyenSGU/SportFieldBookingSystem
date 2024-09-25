package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findRoleByRoleId(int roleId);

}
