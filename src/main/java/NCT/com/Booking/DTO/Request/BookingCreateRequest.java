package NCT.com.Booking.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateRequest {

    private String seatNumner ;
    private LocalDateTime bookingTime  ;
    private boolean status ;
    @NotNull
    private Integer users  ;
    @NotNull
    private Integer flights ;
}
