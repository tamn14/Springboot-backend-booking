package NCT.com.Booking.ConfigJWT;

import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.Service.ServiceImpl.JwtServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;
@Component
public class CustomJwtDecode implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    private JwtServiceImpl jwtService;
    @Autowired
    public CustomJwtDecode(JwtServiceImpl jwtService) {
        this.jwtService = jwtService;
    }

    private NimbusJwtDecoder nimbusJwtDecoder = null ;

    @Override
    public Jwt decode(String token) throws JwtException {
        System.out.println(" Token is : " + token);
        try {
            var response  = jwtService.Instropect(
                    IntrospectRequest.builder().token(token).build()
            );
//            System.out.println(" Token is : " + token + "/n");
//            System.out.println("/n--------------------/n");
//            System.out.println(response + " xxxxx");
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
