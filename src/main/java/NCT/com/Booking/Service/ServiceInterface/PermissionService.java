package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Request.PermissionRequest;
import NCT.com.Booking.DTO.Response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    public PermissionResponse addPermission(PermissionRequest permission) ;
    public PermissionResponse updatePermission(PermissionRequest permission) ;
    public void deletePermission(int id) ;
    public List<PermissionResponse> findAll() ;
    public PermissionResponse findPermissionById(int id) ;

}
