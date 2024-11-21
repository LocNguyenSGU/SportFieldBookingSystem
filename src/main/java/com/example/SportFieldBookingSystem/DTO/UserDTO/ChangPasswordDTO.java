package com.example.SportFieldBookingSystem.DTO.UserDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangPasswordDTO {
    @NotBlank(message = "Access token is required")
    private String accessToken;

    @NotBlank(message = "Password is required")
    private String matKhauCu;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String matKhau;

    @NotBlank(message = "Re-entering password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String reMatKhau;
}
