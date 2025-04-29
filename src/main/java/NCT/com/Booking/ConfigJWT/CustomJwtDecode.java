package NCT.com.Booking.ConfigJWT;

import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.Service.ServiceImpl.AuthenticationServiceImpl;
import NCT.com.Booking.Service.ServiceInterface.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

public class CustomJwtDecode implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null ;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response  = authenticationService.Instropect(
                    IntrospectRequest.builder().token(token).build()
            );
            if(!response.isValid()) throw new JwtException("Token invalid") ;

        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes() , "HS512") ;
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build() ;

        }
        return nimbusJwtDecoder.decode(token) ;
    }
}
