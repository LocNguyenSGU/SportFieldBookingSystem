package com.example.SportFieldBookingSystem.Service.Mapper;

import com.example.SportFieldBookingSystem.DTO.UserDTO.UserBasicDTO;
import com.example.SportFieldBookingSystem.Entity.User;
import com.example.SportFieldBookingSystem.Entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userRoleList", target = "roleNameList", qualifiedByName = "mapUserRoleToRoleNameList")
    UserBasicDTO toBasicDTO(User user);

    @Named("mapUserRoleToRoleNameList")
    default List<String> mapUserRolesToRoleNames(List<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> userRole.getRole().getRoleName()) // Giả định UserRole có phương thức getRoleName
                .collect(Collectors.toList());
    }
}
