package NCT.com.Booking.Mapper;

import NCT.com.Booking.DTO.Request.BookingCreateRequest;

import NCT.com.Booking.DTO.Request.FlightCreateRequest;
import NCT.com.Booking.DTO.Response.BookingResponse;
import NCT.com.Booking.DTO.Response.FlightResponse;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Flights;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Repository.FlightRepo;
import NCT.com.Booking.Repository.UserRepo;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class FlightsMapper {
    private FlightRepo flightRepo ;

    public Flights toEntityCreate(FlightCreateRequest request) {
        Flights flights = new Flights() ;
        flights.setFlightNumber(request.getFlightNumber());
        flights.setAirline(request.getAirline());
        flights.setPrice(request.getPrice());
        flights.setArrivalTime(request.getArrivalTime());
        flights.setDepartureTime(request.getDepartureTime());
        flights.setAvailableSeats(request.getAvailableSeats());
        flights.setFromLocation(request.getFromLocation());
        flights.setToLocation(request.getToLocation());
        return flights ;
    }

    public FlightResponse toDTO(Flights flights) {
        FlightResponse flightResponse = new FlightResponse( );
        flightResponse.setId(flights.getId());
        flightResponse.setAirline(flights.getAirline());
        flightResponse.setDepartureTime(flights.getDepartureTime());
        flightResponse.setFromLocation(flights.getFromLocation());
        flightResponse.setAvailableSeats(flights.getAvailableSeats());
        flightResponse.setPrice(flights.getPrice());
        flightResponse.setToLocation(flights.getToLocation());
        flightResponse.setArrivalTime(flights.getArrivalTime());

        List<Integer> bookingList = new ArrayList<>() ;
        flights.getBookings().forEach(booking -> {
            bookingList.add(booking.getId()) ;
        });

        flightResponse.setBookings(bookingList);

        return flightResponse;
    }


}
