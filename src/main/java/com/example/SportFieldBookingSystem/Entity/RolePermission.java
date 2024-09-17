package com.example.SportFieldBookingSystem.Entity;

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


}
