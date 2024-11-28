package com.example.SportFieldBookingSystem.DTO.RoleDTO;

import com.example.SportFieldBookingSystem.DTO.RolePermissionDTO.RolePermissionCreateDTO;
import com.example.SportFieldBookingSystem.Enum.ActiveEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateDTO {
    @NotNull(message = "id quyền không được để trống")
    private int roleId;

    @NotNull(message = "Tên quyền không được để trống")
    @Size(min = 1, message = "Tên quyền không được để trống")
    private String roleName;

    @NotNull(message = "Chi tiết quyền không được để trống")
    private List<RolePermissionCreateDTO> rolePermissionDTOList;

    @NotNull(message = "trang thai không được để trống")
    private ActiveEnum activeEnum;
}
