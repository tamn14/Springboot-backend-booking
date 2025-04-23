package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.UserRequest;
import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Service.ServiceInterface.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestParam @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.addUsers(userRequest);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(1000, "Success", userResponse);
        return ResponseEntity.ok(apiResponse);
    }

}
