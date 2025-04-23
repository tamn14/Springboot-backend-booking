package NCT.com.Booking.DTO.Request;

import NCT.com.Booking.Entity.Flights;
import NCT.com.Booking.Entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private String seatNumner ;
    private LocalDateTime bookingTime  ;
    private boolean status ;
    @NotNull
    private Integer users  ;
    @NotNull
    private Integer flights ;
}
