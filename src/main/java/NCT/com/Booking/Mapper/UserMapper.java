package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Map từ DTO -> Entity (Request -> Entity)

    @Mapping(source = "bookings", target = "bookings", qualifiedByName = "mapBookingIdsToBookings")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleIdsToRoles")
    Users toUser(UserRequest request);

    // Map từ Entity -> DTO (Entity -> Response)
    @Mapping(source = "bookings", target = "bookings", qualifiedByName = "mapBookingsToBookingIds")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesToRoleIds")
    UserResponse toUserResponse(Users user);

    // ==== Custom Mapping Methods ====

    @Named("mapBookingIdsToBookings")
    default List<Booking> mapBookingIdsToBookings(List<Integer> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Booking booking = new Booking();
            booking.setId(id);
            return booking;
        }).collect(Collectors.toList());
    }

    @Named("mapRoleIdsToRoles")
    default List<Roles> mapRoleIdsToRoles(List<Integer> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Roles role = new Roles();
            role.setId(id);
            return role;
        }).collect(Collectors.toList());
    }

    @Named("mapBookingsToBookingIds")
    default List<Integer> mapBookingsToBookingIds(List<Booking> bookings) {
        if (bookings == null) return null;
        return bookings.stream().map(Booking::getId).collect(Collectors.toList());
    }

    @Named("mapRolesToRoleIds")
    default List<Integer> mapRolesToRoleIds(List<Roles> roles) {
        if (roles == null) return null;
        return roles.stream().map(Roles::getId).collect(Collectors.toList());
    }
}
