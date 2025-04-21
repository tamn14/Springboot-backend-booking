package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Reponse.PermissionReponse;
import NCT.com.Booking.DTO.Reponse.UserReponse;
import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.Entity.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {
    public PermissionReponse addFlight(Permission permission) ;
    public PermissionReponse updateFlight(Permission permission) ;
    public void deleteFlight(int id) ;
    public List<PermissionReponse> findAll() ;
    public PermissionReponse findFlightById(int id) ;

}
