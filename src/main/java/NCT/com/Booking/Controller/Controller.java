package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.PermissionRequest;
import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.PermissionResponse;
import NCT.com.Booking.Service.ServiceInterface.PermissionService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class Controller {
    @Autowired
    private PermissionService permissionService ;
    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        List<PermissionResponse> permissionResponse = permissionService.findAll() ;
        return ApiResponse.<List<PermissionResponse>>builder()
                .mess("Success")
                .result(permissionResponse)
                .build() ;
    }
    @GetMapping("/{id}")
    public ApiResponse<PermissionResponse> getById(@PathVariable int id ) {
        PermissionResponse permissionResponse = permissionService.findPermissionById(id) ;
        return ApiResponse.<PermissionResponse>builder()
                .mess("Success")
                .result(permissionResponse)
                .build() ;
    }
    @PostMapping("/create")
    public ApiResponse<PermissionResponse> createPermission(@Valid @RequestBody PermissionRequest permissionRequest) {
        PermissionResponse permissionResponse = permissionService.addPermission(permissionRequest) ;
        return ApiResponse.<PermissionResponse>builder()
                .mess("Success")
                .result(permissionResponse)
                .build() ;
    }

    @PutMapping("/update/{id}")
    public ApiResponse<PermissionResponse> updatePermission(@Valid @RequestBody PermissionRequest permissionRequest,
                                                            @PathVariable("id") int id)
    {
        PermissionResponse permissionResponse = permissionService.updatePermission(permissionRequest, id) ;
        return ApiResponse.<PermissionResponse>builder()
                .mess("Success")
                .result(permissionResponse)
                .build() ;
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deletePermission(@PathVariable int id ) {
        permissionService.deletePermission(id);
        return ApiResponse.<String>builder()
                .mess("Success")
                .result("Permission has been deleted")
                .build() ;
    }
}
