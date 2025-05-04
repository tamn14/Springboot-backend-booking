package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.*;
import NCT.com.Booking.DTO.Response.AuthenticationResponse;

import NCT.com.Booking.DTO.Response.ExchangeTokenResponse;
import NCT.com.Booking.DTO.Response.UserInfoOauth2;
import NCT.com.Booking.Entity.InvalidatedToken;
import NCT.com.Booking.Entity.Roles;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Mapper.UserMapper;
import NCT.com.Booking.Repository.InvalidateTokenRepo;
import NCT.com.Booking.Repository.OutboundIdentityClient;
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

import javax.management.relation.Role;
import java.text.ParseException;
import java.util.*;


@Slf4j
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepo userRepo;
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService ;
    private InvalidateTokenRepo invalidateTokenRepo ;
    private OutboundIdentityClient outboundIdentityClient ;
    @Autowired
    public AuthenticationServiceImpl(UserRepo userRepo
            , UserMapper userMapper
            , AuthenticationManager authenticationManager
            , JwtService jwtService
            , InvalidateTokenRepo invalidateTokenRepo
            , OutboundIdentityClient outboundIdentityClient)
    {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.invalidateTokenRepo = invalidateTokenRepo;
        this.outboundIdentityClient = outboundIdentityClient;
    }




    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${outbound.identity.redirect-uri}")
    protected String REDIRECT_URI;

    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";




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

    @Override
    public AuthenticationResponse authenticateOAuth2(String code) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("code", code);
        formParams.put("client_id", CLIENT_ID);
        formParams.put("client_secret", CLIENT_SECRET);
        formParams.put("redirect_uri", REDIRECT_URI);
        formParams.put("grant_type", GRANT_TYPE);

        ExchangeTokenResponse response = outboundIdentityClient.exchangeTokenResponse(formParams);
        String accessToken = response.getAccessToken();

        var userInfo = outboundIdentityClient.userInfoOauth2("Bearer " + accessToken);
        String email = userInfo.getEmail() ;
        if(!userRepo.existsByUserName(email)) {
            Users userCreateRequest = new Users() ;
            userCreateRequest.setUserName(email);
            userCreateRequest.setEmail(email);
            Roles roles = new Roles() ;
            roles.setName("User");
            userCreateRequest.getRoles().add(roles);
            userRepo.saveAndFlush(userCreateRequest) ;

        }
        Users users = userRepo.findByUserName(email) ;
        String token = jwtService.generateToken(users) ;

        return  AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
