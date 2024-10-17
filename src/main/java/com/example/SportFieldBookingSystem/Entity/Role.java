package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="role")
public class Role { // vai tro
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private int roleId;
    @Column(name="role_name")
    private String roleName;
    @OneToMany(mappedBy = "role")
    private List<RolePermission> rolePermissionList;
    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoleList;

    public Role() {

    }
    public Role(int roleId, String roleName, List<RolePermission> rolePermissionList, List<UserRole> userRoleList) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.rolePermissionList = rolePermissionList;
        this.userRoleList = userRoleList;
    }
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<RolePermission> getRolePermissionList() {
        return rolePermissionList;
    }

    public void setRolePermissionList(List<RolePermission> rolePermissionList) {
        this.rolePermissionList = rolePermissionList;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }
}
