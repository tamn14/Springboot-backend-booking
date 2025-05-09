package NCT.com.Booking.DTO.Response;

import NCT.com.Booking.Entity.Booking;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {
    private int id ;
    private String fromLocation ;
    private String toLocation ;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime ;
    private String airline ;
    private double price ;
    private int availableSeats ;
    private List<Integer> bookings ;

}
