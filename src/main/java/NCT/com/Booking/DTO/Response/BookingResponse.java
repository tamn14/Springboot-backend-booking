package NCT.com.Booking.DTO.Response;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private int id ;
    private String seatNumner ;
    private LocalDateTime bookingTime  ;
    private boolean status ;
    private Integer users  ;
    private Integer flights ;
}
