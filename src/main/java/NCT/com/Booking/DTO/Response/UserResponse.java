package NCT.com.Booking.DTO.Response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userName ;
    private String passWord ;
    private String lastName ;
    private String firstName ;
    private List<Integer> roles  ;
    private List<Integer> bookings  ;
}
