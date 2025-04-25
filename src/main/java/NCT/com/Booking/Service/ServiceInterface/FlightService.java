package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Request.FlightCreateRequest;
import NCT.com.Booking.DTO.Response.FlightResponse;
import NCT.com.Booking.DTO.Request.FlightUpdateRequest;

import java.util.List;

public interface FlightService {
    public FlightResponse addFlight(FlightCreateRequest flightRequest) ;
    public FlightResponse updateFlight(FlightUpdateRequest flightRequest , int id) ;
    public void deleteFlight(int id) ;
    public List<FlightResponse> findAll() ;
    public FlightResponse findFlightById(int id) ;

}
