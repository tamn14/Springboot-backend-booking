package NCT.com.Booking.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightUpdateRequest {
    @NotBlank
    private String fromLocation ;
    @NotBlank
    private String toLocation ;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime ;
    private String airline ;
    private double price ;
    private int availableSeats ;
    private List<Integer> bookings  ;
}
