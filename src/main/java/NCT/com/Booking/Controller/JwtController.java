package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.IntrospectRequest;
import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.InstropectResponse;
import NCT.com.Booking.Service.ServiceImpl.JwtServiceImpl;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
@RestController
@RequestMapping("/jwt")
public class JwtController {
    @Autowired
    private JwtServiceImpl jwtService ;
    @PostMapping("/introspect")
    ApiResponse<InstropectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = jwtService.Instropect(request);
        return ApiResponse.<InstropectResponse>builder().result(result).build();
    }
}
