package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.FlightRequest;
import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.FlightResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Flights;
import NCT.com.Booking.Entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FlightsMapper {
    @Mapping(source = "bookings", target = "bookings", qualifiedByName = "mapBookingIdsToBookings")
    Flights toFlight(FlightRequest request);
    @Mapping(source = "bookings", target = "bookings", qualifiedByName = "mapBookingsToBookingIds")
    FlightResponse toFlightResponse(Flights flights);

    @Named("mapBookingIdsToBookings")
    default List<Booking> mapBookingIdsToBookings(List<Integer> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Booking booking = new Booking();
            booking.setId(id);
            return booking;
        }).collect(Collectors.toList());
    }
    @Named("mapBookingsToBookingIds")
    default List<Integer> mapBookingsToBookingIds(List<Booking> bookings) {
        if (bookings == null) return null;
        return bookings.stream().map(Booking::getId).collect(Collectors.toList());
    }

}
