package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.FlightRequest;
import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.FlightResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Flights;
import NCT.com.Booking.Entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlightsMapper {
    FlightsMapper INSTANCE = Mappers.getMapper(FlightsMapper.class);
    Flights toFlight(FlightRequest request);
    FlightResponse toFlightResponse(Flights flights);


}
