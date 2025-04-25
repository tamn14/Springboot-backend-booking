package NCT.com.Booking.Controller;

import NCT.com.Booking.DTO.Request.BookingCreateRequest;
import NCT.com.Booking.DTO.Response.ApiResponse;
import NCT.com.Booking.DTO.Response.BookingResponse;
import NCT.com.Booking.DTO.Response.UserResponse;
import NCT.com.Booking.Service.ServiceInterface.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService ;
    @GetMapping
    public ApiResponse<List<BookingResponse>> getAllBooking() {
        List<BookingResponse> bookingResponses = bookingService.findAll() ;
        return ApiResponse.<List<BookingResponse>>builder()
                .mess("Success")
                .result(bookingService.findAll())
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<BookingResponse> getUser(@PathVariable("id") int id ) {
         BookingResponse booking = bookingService.findBookingById(id) ;
        return ApiResponse.<BookingResponse>builder()
                .mess("Success")
                .result(booking)
                .build() ;
    }

    @PostMapping
    public ApiResponse<BookingResponse> addBooking(@RequestBody @Valid  BookingCreateRequest bookingCreateRequest) {
        BookingResponse bookingResponse = bookingService.addBooking(bookingCreateRequest) ;
        return ApiResponse.<BookingResponse>builder()
                .mess("Success")
                .result(bookingResponse)
                .build() ;
    }
    @PutMapping("/{id}")
    public ApiResponse<BookingResponse> updateBooking(@Valid @PathVariable int id
            , @RequestBody BookingCreateRequest bookingCreateRequest  ){
        BookingResponse bookingResponse = bookingService.updateBooking(bookingCreateRequest , id) ;
        return ApiResponse.<BookingResponse>builder()
                .mess("Success")
                .result(bookingResponse)
                .build() ;

    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBooking(@PathVariable int id ) {
        bookingService.deleteBooking(id);
        return ApiResponse.<String>builder()
                .mess("Success")
                .result("Booking has been deleted")
                .build() ;
    }

}
