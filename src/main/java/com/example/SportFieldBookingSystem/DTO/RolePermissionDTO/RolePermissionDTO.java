package com.example.SportFieldBookingSystem.DTO.RolePermissionDTO;

import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import com.example.SportFieldBookingSystem.Enum.RolePermissionActionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDTO {

    private int permissionId;
    private String permissionName;
    private RolePermissionActionEnum action;
    private ActiveEnum trangThaiActive;
}
