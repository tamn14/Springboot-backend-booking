package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.RolesCreationRequest;
import NCT.com.Booking.DTO.Request.RolesUpdateRequest;
import NCT.com.Booking.DTO.Response.RoleResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Permission;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class RoleMapper {
    @Autowired
    private UserRepo userRepo ;

    public Roles toEntity(RolesCreationRequest request) {
        Roles roles = new Roles() ;
        roles.setName(request.getName());
        return roles ;
    }

    // Convert Entity -> Response DTO
    public RoleResponse toDTO(Roles roles) {
        RoleResponse roleResponse = new RoleResponse() ;
        roleResponse.setName(roles.getName());

        Set<Integer> users  = roles.getUsers().stream()
                .map(Users::getId)
                .collect(Collectors.toSet());

        Set<String> permissions = roles.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());

        roleResponse.setUsers(users);
        roleResponse.setPermissions(permissions);
        return roleResponse ;
    }





}
