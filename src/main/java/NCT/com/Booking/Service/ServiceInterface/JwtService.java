package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.DTO.Response.InstropectResponse;
import NCT.com.Booking.Entity.Users;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface JwtService {
    String generateToken(Users users) ;
    SignedJWT verifyToken(String token , boolean isRefress) ;
    String buildScope(Users user) ;
    boolean isTokenExpired(SignedJWT signedJWT , boolean isRefress) throws ParseException;
    InstropectResponse Instropect(IntrospectRequest introspectRequest) ;
}
