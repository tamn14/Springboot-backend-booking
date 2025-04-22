package NCT.com.Booking.DTO.Response;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private String seatNumner ;
    private LocalDateTime bookingTime  ;
    private boolean status ;
    private int users  ;
    private int flights ;
}
