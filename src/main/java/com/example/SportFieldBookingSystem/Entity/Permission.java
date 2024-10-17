package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="permission")
public class Permission { // quyen han
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private int permissionId;
    @Column(name = "permission_name", length = 50)
    private String permissionName;
    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissionList;

    public Permission(){}

    public Permission(int permissionId, String permissionName, List<RolePermission> rolePermissionList) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.rolePermissionList = rolePermissionList;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public List<RolePermission> getRolePermissionList() {
        return rolePermissionList;
    }

    public void setRolePermissionList(List<RolePermission> rolePermissionList) {
        this.rolePermissionList = rolePermissionList;
    }
}
