package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.DTO.Request.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public UserResponse addUsers(UserRequest userRequest) ;
    public UserResponse updateUsers(UserRequest userRequest , int id) ;
    public void deleteUsers(int id) ;
    public List<UserResponse> findAll() ;
    public UserResponse findFlightById(int id) ;

}
