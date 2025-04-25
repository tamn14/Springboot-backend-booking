package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Response.RoleResponse;
import NCT.com.Booking.DTO.Request.RolesRequest;

import java.util.List;

public interface RoleService {
    public RoleResponse addRoles(RolesRequest roleRequest) ;
    public RoleResponse updateRoles(RolesRequest roleRequest) ;
    public void deleteRoles(int id) ;
    public List<RoleResponse> findAll() ;
    public RoleResponse findroleById(int id) ;

}
