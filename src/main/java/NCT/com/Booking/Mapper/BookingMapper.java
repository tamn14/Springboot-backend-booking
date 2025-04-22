package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.BookingRequest;
import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.BookingResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    BookingResponse toBookingResponse(Booking booking);

    Booking toBooking(BookingRequest bookingRequest);
}
