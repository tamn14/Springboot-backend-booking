package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.RoleRequest;
import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.RoleResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.management.relation.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    Roles toRoles(RoleRequest request);
    RoleResponse toUserResponse(Roles roles);


}
