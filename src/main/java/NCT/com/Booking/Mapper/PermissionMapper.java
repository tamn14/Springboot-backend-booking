package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.PermissionRequest;
import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.PermissionResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Permission;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleIdsToRoles")
    Permission toPermission(PermissionRequest request);
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesToRoleIds")
    PermissionResponse toPermissionResponse(Permission permission);

    @Named("mapRoleIdsToRoles")
    default List<Roles> mapRoleIdsToRoles(List<Integer> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Roles role = new Roles();
            role.setId(id);
            return role;
        }).collect(Collectors.toList());
    }
    @Named("mapRolesToRoleIds")
    default List<Integer> mapRolesToRoleIds(List<Roles> roles) {
        if (roles == null) return null;
        return roles.stream().map(Roles::getId).collect(Collectors.toList());
    }
}
