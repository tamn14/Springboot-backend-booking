package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.AuthenticationRequest;
import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.DTO.Request.LogoutRequest;
import NCT.com.Booking.DTO.Request.RefreshRequest;
import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.AuthenticationResponse;
import NCT.com.Booking.DTO.Response.InstropectResponse;
import NCT.com.Booking.Service.ServiceImpl.JwtServiceImpl;
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

    @PostMapping("/outbound/authentication")
    ApiResponse<AuthenticationResponse> outboundAuthenticate(
            @RequestParam("code") String code
    ){
        var result = authenticationService.authenticateOAuth2(code);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        authenticationService.logout(logoutRequest);
        return ApiResponse.<Void>builder()
                .mess("Success")
                .build() ;
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest refreshRequest) throws ParseException, JOSEException {
        AuthenticationResponse authenticationResponse =  authenticationService.Refresh(refreshRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .mess("Success")
                .result(authenticationResponse)
                .build() ;
    }



}
