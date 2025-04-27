package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.PermissionRequest;
import NCT.com.Booking.DTO.Request.RolesCreationRequest;
import NCT.com.Booking.DTO.Response.PermissionResponse;
import NCT.com.Booking.DTO.Response.RoleResponse;
import NCT.com.Booking.Entity.Permission;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {
    @Autowired
    private RoleRepo roleRepo ;

    public Permission toEntity(PermissionRequest request) {
        Permission permission = new Permission() ;
        permission.setName(request.getName());
        Set<Roles> roles = new HashSet<>() ;
        if(!request.getRoles().isEmpty()) {
            request.getRoles().forEach(role -> {
                Roles rolesExisted = roleRepo.findByName(role) ;
                if(rolesExisted != null) {
                    roles.add(rolesExisted) ;
                }
            });
        }
        permission.setRoles(roles);
        return permission;
    }

    // Convert Entity -> Response DTO
    public PermissionResponse toDTO(Permission permission) {
        PermissionResponse permissionResponse = new PermissionResponse() ;
        permissionResponse.setName(permission.getName());
        Set<String> roles = permission.getRoles().stream()
                .map(Roles::getName)
                .collect(Collectors.toSet());
        permissionResponse.setRoles(roles);
        return permissionResponse ;
    }

}
