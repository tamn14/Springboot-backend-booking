package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Response.FlightResponse;
import NCT.com.Booking.DTO.Request.FlightRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FlightService {
    public FlightResponse addFlight(FlightRequest flightRequest) ;
    public FlightResponse updateFlight(FlightRequest flightRequest) ;
    public void deleteFlight(int id) ;
    public List<FlightResponse> findAll() ;
    public FlightResponse findFlightById(int id) ;

}
