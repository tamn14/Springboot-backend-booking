package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.UserCreateRequest;
import NCT.com.Booking.DTO.Request.UsersUpdateRequest;
import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Service.ServiceInterface.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService ;
    @GetMapping
    public ApiResponse<List<UserResponse>> getAll() {
        List<UserResponse> userResponses = userService.findAll() ;
        return ApiResponse.<List<UserResponse>>builder()
                .mess("Success")
                .result(userResponses)
                .build() ;
    }
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getById(@PathVariable int id) {
        UserResponse userResponse = userService.findUserById(id) ;
        return ApiResponse.<UserResponse>builder()
                .mess("Success")
                .result(userResponse)
                .build() ;
    }
    @PostMapping("/create")
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        UserResponse userResponse = userService.addUsers(userCreateRequest) ;
        return ApiResponse.<UserResponse>builder()
                .mess("Success")
                .result(userResponse)
                .build() ;
    }
    @PutMapping("/update/{id}")
    public ApiResponse<UserResponse> updateUser(@RequestBody UsersUpdateRequest usersUpdateRequest
            ,@PathVariable("id") int id) {
        UserResponse userResponse = userService.updateUsers(usersUpdateRequest , id) ;
        return ApiResponse.<UserResponse>builder()
                .mess("Success")
                .result(userResponse)
                .build() ;
    }
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteUser(@PathVariable int id) {
       userService.deleteUsers(id); ;
        return ApiResponse.<String>builder()
                .mess("Success")
                .result("User has been deteled")
                .build() ;
    }


}
