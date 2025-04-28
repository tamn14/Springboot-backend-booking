package NCT.com.Booking.ConfigJWT;

import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.DTO.Response.InstropectResponse;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Repository.UserRepo;
import NCT.com.Booking.exception.AppException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

public class JwtTokenProvider {
    @Autowired
    private UserRepo userRepo ;

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    // method create token
    private String generateToken(Users users) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512) ;
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(users.getUserName())
                .issuer("Huong.com")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + expirationTime * 60 * 1000))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(users))
                .build() ;

        Payload payload = new Payload(jwtClaimsSet.toJSONObject()) ;
        JWSObject jwsObject = new JWSObject(header , payload) ;
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize() ;
        } catch (Exception e) {
            // debug error
            System.out.println("Can not create token : " + e.getMessage());
            throw new RuntimeException("Error generating JWT token", e);
        }
    }
    // method verify token
    private SignedJWT verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token) ;
            if(isTokenExpired(signedJWT)) {
                throw new JOSEException("Token has expired");
            }
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes()) ;

            if (!signedJWT.verify(verifier)) {
                throw new JOSEException("Token verification failed");
            }
            return signedJWT;
        }
        catch (Exception e) {
            System.out.println("Token verification failed: " + e.getMessage());
            throw new RuntimeException("Invalid token", e);
        }


    }
    // method add role and permission into token
    private String buildScope(Users user) {
        StringJoiner stringJoiner = new StringJoiner(" ") ;
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName()) ;
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName()) ;
                    });
                }
            });
        }
        return stringJoiner.toString() ;
    }
    // method get name from token
    public  String getUserNameFromToken(String token) {
        try{
            SignedJWT singJwt = verifyToken(token) ;
            return singJwt.getJWTClaimsSet().getSubject() ;
        }catch (Exception e) {
            return null;
        }
    }
    // method check token is Expired
    public boolean isTokenExpired(SignedJWT signedJWT) throws ParseException {
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return expirationTime.before(new Date());
    }
    // method instrospect token
    public InstropectResponse Instropect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken() ;
        boolean isValid = true ;
        try {
            verifyToken(token);
        } catch (AppException e) {
           isValid = false ;
        }
        return InstropectResponse.builder().valid(isValid).build();
    }



}
