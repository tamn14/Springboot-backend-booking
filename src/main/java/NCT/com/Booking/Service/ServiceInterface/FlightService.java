package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Reponse.FlightReponse;
import NCT.com.Booking.DTO.Request.FlightRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {
    public FlightReponse addFlight(FlightRequest flightRequest) ;
    public FlightReponse updateFlight(FlightRequest flightRequest) ;
    public void deleteFlight(int id) ;
    public List<FlightReponse> findAll() ;
    public FlightReponse findFlightById(int id) ;

}
