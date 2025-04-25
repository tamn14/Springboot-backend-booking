package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Request.UsersUpdateRequest;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.DTO.Request.UserCreateRequest;

import java.util.List;

public interface UserService {
//    public UserResponse addUsers(UserCreateRequest userRequest) ;
//    public UserResponse updateUsers(UsersUpdateRequest userRequest , int id) ;
    public void deleteUsers(int id) ;
    public List<UserResponse> findAll() ;
    public UserResponse findFlightById(int id) ;

}
