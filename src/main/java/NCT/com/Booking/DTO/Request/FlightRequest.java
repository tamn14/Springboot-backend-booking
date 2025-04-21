package NCT.com.Booking.DTO.Request;

import NCT.com.Booking.Entity.Booking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequest {
    private String fromLocation ;
    private String toLocation ;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime ;
    private String airline ;
    private double price ;
    private int availableSeats ;
    private List<Integer> bookings  ;
}
