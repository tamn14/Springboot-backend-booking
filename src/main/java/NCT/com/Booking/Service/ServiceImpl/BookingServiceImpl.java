package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Reponse.BookingReponse;
import NCT.com.Booking.DTO.Request.BookingRequest;
import NCT.com.Booking.Repository.BookingRepo;
import NCT.com.Booking.Service.ServiceInterface.BookingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepo bookingRepo ;

    @Override
    public BookingReponse addFlight(BookingRequest bookingRequest) {
        return null;
    }

    @Override
    public BookingReponse updateFlight(BookingRequest bookingRequest) {
        return null;
    }

    @Override
    public void deleteFlight(int id) {

    }

    @Override
    public List<BookingReponse> findAll() {
        return List.of();
    }

    @Override
    public BookingReponse findFlightById(int id) {
        return null;
    }
}
