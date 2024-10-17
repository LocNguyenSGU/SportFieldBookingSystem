package com.example.SportFieldBookingSystem.Repository;

import com.example.SportFieldBookingSystem.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleId(int roleId);
    boolean existsByRoleName(String roleName);
    boolean existsByRoleNameAndRoleIdNot(String roleName, int roleId);
    @Query("SELECT r.roleId FROM Role r WHERE r.roleName = :roleName")
    int findRoleIdByRoleName(@Param("roleName") String roleName);
    List<Role> findListRoleByUserRoleList_User_Username(String userName);
}
