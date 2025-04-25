package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.FlightResponse;
import NCT.com.Booking.Service.ServiceInterface.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService ;

    @GetMapping
    public ApiResponse<List<FlightResponse>>  findAll(){
        List<FlightResponse> flightResponses = flightService.findAll() ;
        return ApiResponse.<List<FlightResponse>>builder()
                .mess("Success")
                .result(flightResponses)
                .build() ;
    }
}
