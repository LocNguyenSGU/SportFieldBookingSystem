package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {


}
