package com.example.SportFieldBookingSystem.Service;

import com.example.SportFieldBookingSystem.Entity.Permission;
import com.example.SportFieldBookingSystem.Repository.PermissionRepository;
import com.example.SportFieldBookingSystem.Service.Impl.PermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService implements PermissionServiceImpl {
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
}
