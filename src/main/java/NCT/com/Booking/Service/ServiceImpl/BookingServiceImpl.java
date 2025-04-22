package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Response.BookingResponse;
import NCT.com.Booking.DTO.Request.BookingRequest;
import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Flights;
import NCT.com.Booking.Entity.Users;
import NCT.com.Booking.Mapper.BookingMapper;
import NCT.com.Booking.Repository.BookingRepo;
import NCT.com.Booking.Repository.FlightRepo;
import NCT.com.Booking.Repository.UserRepo;
import NCT.com.Booking.Service.ServiceInterface.BookingService;
import NCT.com.Booking.exception.AppException;
import NCT.com.Booking.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private BookingRepo bookingRepo ;
    private BookingMapper bookingMapper ;
    private UserRepo userRepo ;
    private FlightRepo flightRepo ;
    @Autowired
    public BookingServiceImpl(BookingRepo bookingRepo, BookingMapper bookingMapper, UserRepo userRepo, FlightRepo flightRepo) {
        this.bookingRepo = bookingRepo;
        this.bookingMapper = bookingMapper;
        this.userRepo = userRepo;
        this.flightRepo = flightRepo;
    }


    @Override
    public BookingResponse addBooking(BookingRequest bookingRequest) {
        Booking booking = bookingMapper.toBooking(bookingRequest) ;
        Users user = userRepo.findById(booking.getUsers().getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)) ;
        Flights flights = flightRepo.findById(booking.getFlights().getId())
                .orElseThrow(() ->new AppException(ErrorCode.Flight_NOT_EXISTED)) ;
        bookingRepo.saveAndFlush(booking) ;
        return bookingMapper.toBookingResponse(booking) ;
    }

    @Override
    public BookingResponse updateBooking(BookingRequest bookingRequest) {
        Booking booking = bookingMapper.toBooking(bookingRequest);
        Booking bookingUpdate = bookingRepo.findById(bookingRequest.getId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        bookingUpdate.setBookingTime(bookingRequest.getBookingTime());
        bookingUpdate.setSeatNumner(bookingRequest.getSeatNumner());
        bookingUpdate.setStatus(bookingRequest.isStatus());
        if(bookingRequest.getUsers() != bookingUpdate.getUsers().getId()) {
            Users userUpdate = userRepo.findById(bookingRequest.getUsers())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)) ;
            bookingUpdate.getUsers().deleteBooking(booking);
            bookingUpdate.getUsers().addBooking(bookingUpdate);
        }
        if(bookingRequest.getFlights() != bookingUpdate.getFlights().getId()) {
            Flights flightUpdate = flightRepo.findById(booking.getFlights().getId())
                    .orElseThrow(() ->new AppException(ErrorCode.Flight_NOT_EXISTED)) ;
            bookingUpdate.getFlights().deleteBooking(booking);
            bookingUpdate.getFlights().addBooking(bookingUpdate);
        }
        bookingRepo.saveAndFlush(bookingUpdate) ;
        return bookingMapper.toBookingResponse(bookingUpdate);

    }

    @Override
    public void deleteBooking(int id) {
        Booking bookingDelete= bookingRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        bookingRepo.deleteById(id);

    }

    @Override
    public List<BookingResponse> findAll() {
        List<Booking> bookings = bookingRepo.findAll();
        return bookings.stream()
                .map(booking -> bookingMapper.toBookingResponse(booking))
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse findFlightById(int id) {
        Booking booking= bookingRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        return bookingMapper.toBookingResponse(booking);

    }
}
