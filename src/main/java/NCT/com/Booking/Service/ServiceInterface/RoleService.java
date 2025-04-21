package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Reponse.RoleReponse;
import NCT.com.Booking.DTO.Reponse.UserReponse;
import NCT.com.Booking.DTO.Request.RoleRequest;
import NCT.com.Booking.DTO.Request.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    public RoleReponse addFlight(RoleRequest roleRequest) ;
    public RoleReponse updateFlight(RoleRequest roleRequest) ;
    public void deleteFlight(int id) ;
    public List<RoleReponse> findAll() ;
    public RoleReponse findFlightById(int id) ;

}
