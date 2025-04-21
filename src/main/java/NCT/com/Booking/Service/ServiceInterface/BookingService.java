package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Reponse.BookingReponse;
import NCT.com.Booking.DTO.Reponse.FlightReponse;
import NCT.com.Booking.DTO.Request.BookingRequest;
import NCT.com.Booking.DTO.Request.FlightRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    public BookingReponse addFlight(BookingRequest bookingRequest) ;
    public BookingReponse updateFlight(BookingRequest bookingRequest) ;
    public void deleteFlight(int id) ;
    public List<BookingReponse> findAll() ;
    public BookingReponse findFlightById(int id) ;
}
