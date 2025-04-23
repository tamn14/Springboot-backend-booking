package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.RoleRequest;
import NCT.com.Booking.DTO.Response.RoleResponse;
import NCT.com.Booking.Entity.Permission;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "users", expression = "java(mapUserIds(roles.getUsers()))")
    @Mapping(target = "permissions", expression = "java(mapPermissionIds(roles.getPermissions()))")
    RoleResponse toUserResponse(Roles roles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", expression = "java(mapUsers(request.getUsers()))")
    @Mapping(target = "permissions", expression = "java(mapPermissions(request.getPermissions()))")
    Roles toRoles(RoleRequest request);

    // ----------- Helper methods ------------------

    default Users mapUser(Integer id) {
        if (id == null) return null;
        Users user = new Users();
        user.setId(id);
        return user;
    }

    default Permission mapPermission(Integer id) {
        if (id == null) return null;
        Permission permission = new Permission();
        permission.setId(id);
        return permission;
    }

    default List<Users> mapUsers(List<Integer> ids) {
        if (ids == null) return null;
        List<Users> list = new ArrayList<>();
        for (Integer id : ids) {
            list.add(mapUser(id));
        }
        return list;
    }

    default List<Permission> mapPermissions(List<Integer> ids) {
        if (ids == null) return null;
        List<Permission> list = new ArrayList<>();
        for (Integer id : ids) {
            list.add(mapPermission(id));
        }
        return list;
    }

    default List<Integer> mapUserIds(List<Users> users) {
        if (users == null) return null;
        List<Integer> ids = new ArrayList<>();
        for (Users u : users) {
            ids.add(u.getId());
        }
        return ids;
    }

    default List<Integer> mapPermissionIds(List<Permission> permissions) {
        if (permissions == null) return null;
        List<Integer> ids = new ArrayList<>();
        for (Permission p : permissions) {
            ids.add(p.getId());
        }
        return ids;
    }
}
