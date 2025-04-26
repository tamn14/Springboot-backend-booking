package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.FlightCreateRequest;
import NCT.com.Booking.DTO.Request.FlightUpdateRequest;
import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.FlightResponse;
import NCT.com.Booking.Service.ServiceInterface.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public ApiResponse<FlightResponse> finfById(@PathVariable int id )  {
        FlightResponse flightResponse = flightService.findFlightById(id) ;
        return ApiResponse.<FlightResponse>builder()
                .mess("Success")
                .result(flightResponse)
                .build() ;

    }

    @PostMapping("/create")
    public ApiResponse<FlightResponse> createFlight(@Valid @RequestBody FlightCreateRequest flightCreateRequest) {
        FlightResponse flightResponse = flightService.addFlight(flightCreateRequest) ;
        return ApiResponse.<FlightResponse>builder()
                .mess("Success")
                .result(flightResponse)
                .build() ;
    }

    @PutMapping("/{id}")
    public ApiResponse<FlightResponse> updateFlight( @RequestBody FlightUpdateRequest flightUpdateRequest
            , @PathVariable  int id){
        FlightResponse flightResponse = flightService.updateFlight(flightUpdateRequest , id) ;
        return ApiResponse.<FlightResponse>builder()
                .mess("Success")
                .result(flightResponse)
                .build() ;
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteFlight(@PathVariable int id) {
        flightService.deleteFlight(id);
        return ApiResponse.<String>builder()
                .mess("Success")
                .result("Flight has been deleted")
                .build() ;
    }
}
