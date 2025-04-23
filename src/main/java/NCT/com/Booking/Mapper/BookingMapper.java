package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.BookingRequest;
import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.BookingResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Flights;
import NCT.com.Booking.Entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    // Map Entity -> Response
    @Mapping(source = "users.id", target = "users")
    @Mapping(source = "flights.id", target = "flights")
    BookingResponse toBookingResponse(Booking booking);

    // Map Request -> Entity (khi đã có Users & Flights)
    @Mapping(target = "id", ignore = true)
    Booking toBooking(BookingRequest request,
                      @MappingTarget Booking booking);

    // Hoặc nếu muốn map nhanh bằng id (rất giới hạn)
    @Mapping(target = "users.id", source = "users")
    @Mapping(target = "flights.id", source = "flights")
    Booking mapFromRequestWithIds(BookingRequest request);

    default Users map(Integer id) {
        if (id == null) return null;
        Users user = new Users();
        user.setId(id);
        return user;
    }

    default Flights mapFlights(Integer id) {
        if (id == null) return null;
        Flights flight = new Flights();
        flight.setId(id);
        return flight;
    }
}
