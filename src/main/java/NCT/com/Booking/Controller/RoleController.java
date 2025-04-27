package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.RolesCreationRequest;
import NCT.com.Booking.DTO.Request.RolesUpdateRequest;
import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.RoleResponse;
import NCT.com.Booking.Repository.RoleRepo;
import NCT.com.Booking.Service.ServiceInterface.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService ;
    @GetMapping("/{id}")
    public ApiResponse<RoleResponse> getByID(@PathVariable int id) {
        RoleResponse roleResponse = roleService.findroleById(id) ;
        return ApiResponse.<RoleResponse>builder()
                .mess("Success")
                .result(roleResponse)
                .build() ;

    }
    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        List<RoleResponse> roleResponse = roleService.findAll() ;
        return ApiResponse.<List<RoleResponse>>builder()
                .mess("Success")
                .result(roleResponse)
                .build() ;

    }
    @PostMapping("/create")
    public ApiResponse<RoleResponse> createRole(@Valid @RequestBody RolesCreationRequest rolesCreationRequest) {
        RoleResponse roleResponse = roleService.addRoles(rolesCreationRequest) ;
        return ApiResponse.<RoleResponse>builder()
                .mess("Success")
                .result(roleResponse)
                .build() ;

    }
    @PutMapping("/update/{id}")
    public ApiResponse<RoleResponse> updateRole(@Valid @RequestBody RolesUpdateRequest rolesUpdateRequest
            , @PathVariable int id) {
        RoleResponse roleResponse = roleService.updateRoles(rolesUpdateRequest , id) ;
        return ApiResponse.<RoleResponse>builder()
                .mess("Success")
                .result(roleResponse)
                .build() ;

    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteRole(@PathVariable int id) {
        roleService.deleteRoles(id);
        return ApiResponse.<String>builder()
                .mess("Success")
                .result("Role has been deleted")
                .build() ;
    }


}
