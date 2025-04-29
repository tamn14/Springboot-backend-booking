package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.AuthenticationRequest;
import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.AuthenticationResponse;
import NCT.com.Booking.DTO.Response.InstropectResponse;
import NCT.com.Booking.Service.ServiceInterface.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService ;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest) ;
        return ApiResponse.<AuthenticationResponse>builder()
                .mess("Success")
                .result(authenticationResponse)
                .build() ;
    }
    @PostMapping("/introspect")
    ApiResponse<InstropectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.Instropect(request);
        return ApiResponse.<InstropectResponse>builder().result(result).build();
    }

}
