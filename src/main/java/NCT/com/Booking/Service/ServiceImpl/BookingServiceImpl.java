package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.BookingCreateRequest;
import NCT.com.Booking.DTO.Response.BookingResponse;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    private BookingRepo bookingRepo ;
    private UserRepo userRepo ;
    private FlightRepo flightRepo ;
    private BookingMapper bookingMapper ;
    private QrServiceImpl qrCode ;
    private EmailServiceImpl emailService ;
    private ObjectMapper objectMapper;
    @Autowired
    public BookingServiceImpl(BookingRepo bookingRepo, UserRepo userRepo, FlightRepo flightRepo, BookingMapper bookingMapper, QrServiceImpl qrCode, EmailServiceImpl emailService, ObjectMapper objectMapper) {
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
        this.flightRepo = flightRepo;
        this.bookingMapper = bookingMapper;
        this.qrCode = qrCode;
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    @Value("${Qr.width}")
    private int widthQr ;
    @Value("${Qr.height}")
    private int heightQr ;
    @Value("${mail.from}")
    private String mailForm ;
    @Value("${mail.from}")
    private String mailTo ;






    @Override
    public BookingResponse addBooking(BookingCreateRequest bookingRequest) {
        Booking booking = bookingMapper.toEntity(bookingRequest);
        Users users = userRepo.findById(bookingRequest.getUsers())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)) ;
        Flights flights = flightRepo.findById(bookingRequest.getFlights())
                .orElseThrow(()-> new AppException(ErrorCode.Flight_NOT_EXISTED)) ;
        users.getBookings().add(booking) ;
        booking.setUsers(users);
        flights.getBookings().add(booking) ;
        booking.setFlights(flights);
        bookingRepo.saveAndFlush(booking) ;
        try {
            String jsonTickit = objectMapper.writeValueAsString(booking) ;
            byte[] qr = qrCode.generateQRCodeToFile(jsonTickit, widthQr , heightQr ) ;
            emailService.SendMessage(mailForm , mailTo , qr  );

        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }

        return bookingMapper.toDTO(booking) ;
    }

    @Override

    public BookingResponse updateBooking(BookingCreateRequest bookingRequest , int id) {
        Booking booking = bookingMapper.toEntity(bookingRequest);
        Booking bookingUpdate = bookingRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        bookingUpdate.setSeatNumner(bookingRequest.getSeatNumner());
        bookingUpdate.setStatus(bookingRequest.isStatus());
        bookingUpdate.setBookingTime(bookingRequest.getBookingTime());
        // Kiem tra co muon cap nhat users khong
        if(bookingRequest.getUsers() != null) {
            Users users = userRepo.findById(bookingRequest.getUsers())
                    .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)) ;
            // Xoa moi quan he giua user va booking cu
            bookingUpdate.getUsers().getBookings().remove(bookingUpdate);
            bookingUpdate.setUsers(null);
            // them user moi
            bookingUpdate.setUsers(users);
            users.getBookings().add(bookingUpdate) ;
        }

        if(bookingRequest.getFlights() != null) {
            Flights flights = flightRepo.findById(bookingRequest.getFlights())
                    .orElseThrow(()-> new AppException(ErrorCode.Flight_NOT_EXISTED)) ;
            // Xoa moi quan he giua flight va booking cu
            bookingUpdate.getFlights().getBookings().remove(bookingUpdate) ;
            bookingUpdate.setFlights(null);
            // them flight moi
            bookingUpdate.setFlights(flights);
            flights.getBookings().add(bookingUpdate) ;
        }
        bookingRepo.saveAndFlush(bookingUpdate);
        return bookingMapper.toDTO(bookingUpdate) ;

    }

    @Override
    public void deleteBooking(int id) {
        Booking bookingUpdate = bookingRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        bookingRepo.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingResponse> findAll() {
        return bookingRepo.findAll().stream()
                .map(bookingMapper::toDTO).toList() ;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BookingResponse findBookingById(int id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        return bookingMapper.toDTO(booking);
    }

    @Override
    public List<BookingResponse> getMyBooking() {
        var context = SecurityContextHolder.getContext() ;
        String name = context.getAuthentication().getName() ;
        Users users = userRepo.findByUserName(name) ;
        if(users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED) ;
        }
        List<Booking> booking = bookingRepo.findAllByUsers(users) ;
        // kiem tra co phai la booking cua user dang dang nhap khong
        if(!(booking.get(0).getUsers().getId() == users.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED) ;
        }
        return booking.stream()
                .map(bookingMapper::toDTO).toList() ;
    }






}
