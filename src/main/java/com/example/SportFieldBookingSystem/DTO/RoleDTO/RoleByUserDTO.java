package com.example.SportFieldBookingSystem.DTO.RoleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleByUserDTO {
    private String email;
    private String roleName;
}
