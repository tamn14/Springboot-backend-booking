package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.DTO.Request.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public UserResponse addFlight(UserRequest userRequest) ;
    public UserResponse updateFlight(UserRequest userRequest) ;
    public void deleteFlight(int id) ;
    public List<UserResponse> findAll() ;
    public UserResponse findFlightById(int id) ;

}
