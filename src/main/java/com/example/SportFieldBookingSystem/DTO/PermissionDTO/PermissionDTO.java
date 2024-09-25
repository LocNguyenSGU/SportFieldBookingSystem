package com.example.SportFieldBookingSystem.DTO.PermissionDTO;

public class PermissionDTO {
    private int permissionId;
    private String permissionName;

    // Constructor
    public PermissionDTO(int permissionId, String permissionName) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
    }

    // Getters and Setters
    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
