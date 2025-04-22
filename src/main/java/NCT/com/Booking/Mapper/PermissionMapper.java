package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.PermissionRequest;
import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.PermissionResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Permission;
import NCT.com.Booking.Entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);


}
