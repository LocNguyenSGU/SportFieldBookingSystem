package com.example.SportFieldBookingSystem.Service.Impl;

import com.example.SportFieldBookingSystem.Entity.Permission;
import com.example.SportFieldBookingSystem.Repository.PermissionRepository;
import com.example.SportFieldBookingSystem.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public Permission getPermissionByPermissionId(int permissionId) {
        try{
            Permission permission = permissionRepository.findPermissionByPermissionId(permissionId);
            return permission;
        }catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public int getPermissionIdByPermissionName(String permissionName) {
        try {
            return permissionRepository.findPermissionIdByPermissionName(permissionName);
        }catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

}
