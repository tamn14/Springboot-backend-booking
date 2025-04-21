package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Reponse.FlightReponse;
import NCT.com.Booking.DTO.Reponse.UserReponse;
import NCT.com.Booking.DTO.Request.FlightRequest;
import NCT.com.Booking.DTO.Request.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public UserReponse addFlight(UserRequest userRequest) ;
    public UserReponse updateFlight(UserRequest userRequest) ;
    public void deleteFlight(int id) ;
    public List<UserReponse> findAll() ;
    public UserReponse findFlightById(int id) ;

}
