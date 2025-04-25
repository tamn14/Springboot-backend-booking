package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.BookingCreateRequest;
import NCT.com.Booking.DTO.Response.BookingResponse;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Flights;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.DTO.Response.FlightResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Repository.FlightRepo;
import NCT.com.Booking.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final UserRepo usersRepository;
    private final FlightRepo flightsRepository;

    public Booking toEntity(BookingCreateRequest request) {
        Booking booking = new Booking();
        booking.setSeatNumner(request.getSeatNumner());
        booking.setBookingTime(request.getBookingTime());
        booking.setStatus(request.isStatus());

        // Map ID thành Entity
        Users user = usersRepository.findById(request.getUsers())
                .orElseThrow(() -> new RuntimeException("User not found with id " + request.getUsers()));
        Flights flight = flightsRepository.findById(request.getFlights())
                .orElseThrow(() -> new RuntimeException("Flight not found with id " + request.getFlights()));

        booking.setUsers(user);
        booking.setFlights(flight);

        return booking;
    }

    public BookingResponse toDTO(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setSeatNumner(booking.getSeatNumner());
        response.setBookingTime(booking.getBookingTime());
        response.setStatus(booking.isStatus());
        response.setUsers(booking.getUsers().getId());
        response.setFlights(booking.getFlights().getId());
        return response;
    }
}
