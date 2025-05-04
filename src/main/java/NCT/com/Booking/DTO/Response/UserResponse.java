package NCT.com.Booking.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String userName ;
    private String passWord ;
    private String lastName ;
    private String firstName ;
    private String email ;
    private Set<String> roles  ;
    private List<Integer> bookings  ;
}
