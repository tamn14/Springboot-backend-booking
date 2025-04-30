package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.DTO.Response.InstropectResponse;
import NCT.com.Booking.Entity.Users;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface JwtService {
    String generateToken(Users users) ;
    SignedJWT verifyToken(String token) ;
    String buildScope(Users user) ;
    String getUserNameFromToken(String token) ;
    boolean isTokenExpired(SignedJWT signedJWT) throws ParseException;
    InstropectResponse Instropect(IntrospectRequest introspectRequest) ;
}
