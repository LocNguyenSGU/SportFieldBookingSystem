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
}
