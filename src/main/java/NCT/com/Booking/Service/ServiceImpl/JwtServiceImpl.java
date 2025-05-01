package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.DTO.Response.InstropectResponse;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Repository.InvalidateTokenRepo;
import NCT.com.Booking.Service.ServiceInterface.JwtService;
import NCT.com.Booking.exception.AppException;
import NCT.com.Booking.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Autowired
    private InvalidateTokenRepo invalidateTokenRepo ;
    @NonFinal
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;
    @NonFinal
    @Value("${jwt.expirationTime}")
    private long expirationTime;
    @NonFinal
    @Value("${jwt.refreshable-duration}")
    private long refreshTime;
    // method create token
    @Override
    public String generateToken(Users users) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(users.getUserName())
                .issuer("Huong.com")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + expirationTime * 60 * 1000))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(users))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (Exception e) {
            // debug error
            log.error("Can not create token : " + e.getMessage());
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    // method verify token
    @Override
    public SignedJWT verifyToken(String token , boolean isRefresh) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            // token is expired ?
            if (isTokenExpired(signedJWT , isRefresh)) {
                throw new JOSEException("Token has expired");
            }
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            // signKey not true ?
            if (!signedJWT.verify(verifier)) {
                throw new JOSEException("Token verification failed");
            }
            // token had been logout ?
            if(invalidateTokenRepo.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            return signedJWT;
        } catch (Exception e) {
            log.error("Token verification failed: " + e.getMessage());
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

    }

    // method add role and permission into token
    @Override
    public String buildScope(Users user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }

    // method get name from token
    public String getUserNameFromToken(String token) {
        try {
            SignedJWT singJwt = verifyToken(token , true);
            return singJwt.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    // method check token is Expired
    @Override
    public boolean isTokenExpired(SignedJWT signedJWT , boolean isRefress) throws ParseException {
        Date expirationTime =(isRefress)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(refreshTime, ChronoUnit.MINUTES)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        return expirationTime.before(new Date());
    }

    // method instrospect token
    @Override
    public InstropectResponse Instropect(IntrospectRequest introspectRequest) {
        var token = introspectRequest.getToken();
        boolean isValid = true;
        try {
            verifyToken(token , false);

        } catch (AppException e) {
            isValid = false;
        }
        return InstropectResponse.builder().valid(isValid).build();
    }
}
