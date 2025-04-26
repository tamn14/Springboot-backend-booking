package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Request.RolesCreationRequest;
import NCT.com.Booking.DTO.Response.RoleResponse;
import NCT.com.Booking.DTO.Request.RolesUpdateRequest;

import java.util.List;

public interface RoleService {
    public RoleResponse addRoles(RolesCreationRequest roleRequest) ;
    public RoleResponse updateRoles(RolesUpdateRequest roleRequest , int id) ;
    public void deleteRoles(int id) ;
    public List<RoleResponse> findAll() ;
    public RoleResponse findroleById(int id) ;

}
