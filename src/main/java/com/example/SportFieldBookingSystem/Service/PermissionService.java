package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.Entity.Permission;
import com.example.SportFieldBookingSystem.Repository.PermissionRepository;
import com.example.SportFieldBookingSystem.Service.Impl.PermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface PermissionService {

    Permission getPermissionByPermissionId(int permissionId);

    int getPermissionIdByPermissionName(String permissionName);
}
