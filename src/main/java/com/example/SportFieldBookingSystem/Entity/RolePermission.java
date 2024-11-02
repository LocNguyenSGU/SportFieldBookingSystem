package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="role_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
