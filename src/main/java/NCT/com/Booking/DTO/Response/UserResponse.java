package NCT.com.Booking.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int id ;
    private String userName ;
    private String passWord ;
    private String lastName ;
    private String firstName ;
    private Set<RoleResponse> roles  ;
    private List<BookingResponse> bookings  ;
}
