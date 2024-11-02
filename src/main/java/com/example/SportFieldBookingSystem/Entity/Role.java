package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="role")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private ActiveEnum trangThaiActive;
}
