package NCT.com.Booking.DTO.Reponse;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingReponse {
    private String seatNumner ;
    private LocalDateTime bookingTime  ;
    private boolean status ;
    private int users  ;
    private int flights ;
}
