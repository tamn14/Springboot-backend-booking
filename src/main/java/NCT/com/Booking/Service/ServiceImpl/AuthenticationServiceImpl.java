package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.AuthenticationRequest;
import NCT.com.Booking.DTO.Response.AuthenticationResponse;

import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Mapper.UserMapper;
import NCT.com.Booking.Repository.UserRepo;
import NCT.com.Booking.Service.ServiceInterface.AuthenticationService;
import NCT.com.Booking.Service.ServiceInterface.JwtService;
import NCT.com.Booking.exception.AppException;
import NCT.com.Booking.exception.ErrorCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepo userRepo;
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService ;
    @Autowired
    public AuthenticationServiceImpl(UserRepo userRepo, UserMapper userMapper, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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


}
