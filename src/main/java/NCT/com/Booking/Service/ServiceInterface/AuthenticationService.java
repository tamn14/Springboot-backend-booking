package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Request.AuthenticationRequest;
import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.DTO.Request.LogoutRequest;
import NCT.com.Booking.DTO.Request.RefreshRequest;
import NCT.com.Booking.DTO.Response.AuthenticationResponse;
import NCT.com.Booking.DTO.Response.InstropectResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Repository.UserRepo;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.ParseException;

public interface AuthenticationService {
    public AuthenticationResponse authenticate (AuthenticationRequest authenticationRequest)  ;
    public void logout(LogoutRequest logoutRequest) ;
    public AuthenticationResponse Refresh(RefreshRequest refreshRequest) throws ParseException, JOSEException;

}
