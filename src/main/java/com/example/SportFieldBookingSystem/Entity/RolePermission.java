package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionEnum;
import jakarta.persistence.*;

@Entity
@Table(name="role_permission")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_permission_id")
    private int rolePermissionId;
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
    @ManyToOne
    @JoinColumn(name="permission_id")
    private Permission permission;

    @Column(name = "action", length = 20)
    @Enumerated(EnumType.STRING)
    private RolePermissionActionEnum action;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private RolePermissionEnum status;

    public RolePermission() {

    }

    public RolePermission(int rolePermissionId, Role role, Permission permission, RolePermissionActionEnum action) {
        this.rolePermissionId = rolePermissionId;
        this.role = role;
        this.permission = permission;
        this.action = action;
    }

    public int getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(int rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public RolePermissionActionEnum getAction() {
        return action;
    }

    public void setAction(RolePermissionActionEnum action) {
        this.action = action;
    }

    public RolePermissionEnum getStatus() {
        return status;
    }

    public void setStatus(RolePermissionEnum status) {
        this.status = status;
    }
}
