package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Response.PermissionResponse;
import NCT.com.Booking.Entity.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PermissionService {
    public PermissionResponse addFlight(Permission permission) ;
    public PermissionResponse updateFlight(Permission permission) ;
    public void deleteFlight(int id) ;
    public List<PermissionResponse> findAll() ;
    public PermissionResponse findFlightById(int id) ;

}
