package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.DTO.Request.FlightCreateRequest;
import NCT.com.Booking.DTO.Request.FlightUpdateRequest;
import NCT.com.Booking.DTO.Response.FlightResponse;
import NCT.com.Booking.Entity.Flights;
import NCT.com.Booking.Mapper.FlightsMapper;
import NCT.com.Booking.Repository.FlightRepo;
import NCT.com.Booking.Service.ServiceInterface.FlightService;
import NCT.com.Booking.exception.AppException;
import NCT.com.Booking.exception.ErrorCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private FlightRepo flightRepo ;
    private FlightsMapper flightsMapper ;
    @Autowired
    public FlightServiceImpl(FlightRepo flightRepo, FlightsMapper flightsMapper) {
        this.flightRepo = flightRepo;
        this.flightsMapper = flightsMapper;
    }

    @Override
    public FlightResponse addFlight(FlightCreateRequest flightRequest) {
        Flights flights = flightsMapper.toEntityCreate(flightRequest);
        Flights flightExisted = flightRepo.findById(flightRequest.getId())
                .orElseThrow(() -> new AppException(ErrorCode.Flight_NOT_EXISTED)) ;
        flightRepo.save(flights) ;
        return flightsMapper.toDTO(flights) ;
    }

    @Override
    public FlightResponse updateFlight(FlightUpdateRequest flightRequest , int id) {
        Flights flights = flightRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.Flight_NOT_EXISTED)) ;
        flights.setFlightNumber(flights.getFlightNumber());
        flights.setPrice(flightRequest.getPrice());
        flights.setToLocation(flightRequest.getToLocation());
        flights.setArrivalTime(flightRequest.getArrivalTime());
        flights.setFromLocation(flightRequest.getFromLocation());
        flights.setAirline(flightRequest.getAirline());
        flights.setDepartureTime(flightRequest.getDepartureTime());
        flights.setAvailableSeats(flightRequest.getAvailableSeats());
        return flightsMapper.toDTO(flights) ;
    }

    @Override
    public void deleteFlight(int id) {
        Flights flights = flightRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.Flight_NOT_EXISTED)) ;
        flightRepo.deleteById(id);
    }

    @Override
    public List<FlightResponse> findAll() {
        return flightRepo.findAll().stream()
                .map(flightsMapper::toDTO).toList() ;
    }

    @Override
    public FlightResponse findFlightById(int id) {
        Flights flights = flightRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.Flight_NOT_EXISTED)) ;
        return flightsMapper.toDTO(flights) ;
    }
}
