package NCT.com.Booking.Service.ServiceInterface;

import NCT.com.Booking.DTO.Request.BookingCreateRequest;
import NCT.com.Booking.DTO.Response.BookingResponse;

import java.util.List;


public interface BookingService {
    public BookingResponse addBooking(BookingCreateRequest bookingRequest) ;
    public BookingResponse updateBooking(BookingCreateRequest bookingRequest , int id) ;
    public void deleteBooking(int id) ;
    public List<BookingResponse> findAll() ;
    public BookingResponse findBookingById(int id) ;
}
