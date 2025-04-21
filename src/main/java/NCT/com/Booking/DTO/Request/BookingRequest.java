package NCT.com.Booking.DTO.Request;

import NCT.com.Booking.Entity.Flights;
import NCT.com.Booking.Entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private String seatNumner ;
    private LocalDateTime bookingTime  ;
    private boolean status ;
    private int users  ;
    private int flights ;
}
