package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Permission findPermissionByPermissionId(int permissionId);
    @Query("SELECT p.permissionId FROM Permission p WHERE p.permissionName = :permissionName")
    int findPermissionIdByPermissionName(@Param("permissionName") String permissionName);
}
