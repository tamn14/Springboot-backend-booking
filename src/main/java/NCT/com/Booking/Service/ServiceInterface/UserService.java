package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Request.UsersUpdateRequest;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.DTO.Request.UserCreateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public UserResponse addUsers(UserCreateRequest userRequest) ;
    public UserResponse updateUsers(UsersUpdateRequest userRequest , int id) ;
    public void deleteUsers(int id) ;
    public List<UserResponse> findAll() ;
    public UserResponse findUserById(int id) ;
    public UserResponse findByUserName(String UserName) ;

}
