package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.AuthenticationRequest;
import NCT.com.Booking.DTO.Request.LogoutRequest;
import NCT.com.Booking.DTO.Request.RefreshRequest;
import NCT.com.Booking.DTO.Response.AuthenticationResponse;

import NCT.com.Booking.Entity.InvalidatedToken;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Mapper.UserMapper;
import NCT.com.Booking.Repository.InvalidateTokenRepo;
import NCT.com.Booking.Repository.UserRepo;
import NCT.com.Booking.Service.ServiceInterface.AuthenticationService;
import NCT.com.Booking.Service.ServiceInterface.JwtService;
import NCT.com.Booking.exception.AppException;
import NCT.com.Booking.exception.ErrorCode;
import com.nimbusds.jose.JOSEException;
import jakarta.transaction.Transactional;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;


@Slf4j
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepo userRepo;
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService ;
    private InvalidateTokenRepo invalidateTokenRepo ;
    @Autowired
    public AuthenticationServiceImpl(UserRepo userRepo
            , UserMapper userMapper
            , AuthenticationManager authenticationManager
            , JwtService jwtService
            , InvalidateTokenRepo invalidateTokenRepo) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.invalidateTokenRepo = invalidateTokenRepo;
    }



    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        Users user = userRepo.findByUserName(authenticationRequest.getUsername());
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    @Override
    public AuthenticationResponse Refresh(RefreshRequest refreshRequest) throws ParseException, JOSEException {
        // chuyen token hien tai vao blacklist
        var signedJwt  = jwtService.verifyToken(refreshRequest.getToken() ,  true);
        var jit = signedJwt.getJWTClaimsSet().getJWTID() ;
        var expiryTime = signedJwt.getJWTClaimsSet().getExpirationTime() ;
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build() ;
        invalidateTokenRepo.save(invalidatedToken) ;
        // refress token
        var username =  signedJwt.getJWTClaimsSet().getSubject() ;
        var user = userRepo.findByUserName(username) ;
        if(user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED) ;
        }
        var token = jwtService.generateToken(user) ;
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build() ;
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        try {
            var signToken = jwtService.verifyToken(logoutRequest.getToken(), false) ;
            // lay ma token
            String jit = signToken.getJWTClaimsSet().getJWTID() ;
            var expiryTime  = signToken.getJWTClaimsSet().getExpirationTime() ;
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build() ;
            invalidateTokenRepo.save(invalidatedToken) ;
        } catch (AppException | ParseException exception) {
            log.info("Token already expired") ;
        }
    }
}
