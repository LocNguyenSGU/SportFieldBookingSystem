package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.RolePermission;
import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    boolean existsByRole_RoleIdAndPermission_PermissionIdAndActionAndStatus(int roleId, int permissionId, RolePermissionActionEnum action, ActiveEnum status);
}
