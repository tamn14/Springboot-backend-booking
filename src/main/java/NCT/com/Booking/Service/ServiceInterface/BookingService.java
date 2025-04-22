package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Response.BookingResponse;
import NCT.com.Booking.DTO.Request.BookingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    public BookingResponse addBooking(BookingRequest bookingRequest) ;
    public BookingResponse updateBooking(BookingRequest bookingRequest) ;
    public void deleteBooking(int id) ;
    public List<BookingResponse> findAll() ;
    public BookingResponse findFlightById(int id) ;
}
