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
}
