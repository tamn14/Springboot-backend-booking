package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Response.RoleResponse;
import NCT.com.Booking.DTO.Request.RoleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleService {
    public RoleResponse addFlight(RoleRequest roleRequest) ;
    public RoleResponse updateFlight(RoleRequest roleRequest) ;
    public void deleteFlight(int id) ;
    public List<RoleResponse> findAll() ;
    public RoleResponse findFlightById(int id) ;

}
