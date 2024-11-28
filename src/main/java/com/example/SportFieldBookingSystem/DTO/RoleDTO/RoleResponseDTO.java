package com.example.SportFieldBookingSystem.DTO.RoleDTO;

import com.example.SportFieldBookingSystem.DTO.PermissionDTO.PermissionDTO;
import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionDTO;
import com.example.SportFieldBookingSystem.Entity.RolePermission;
import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDTO {
    private int idQuyen;
    private String tenQuyen;
    private List<RolePermissionDTO> rolePermissionDTOList;
    private ActiveEnum trangThaiActive;
}
