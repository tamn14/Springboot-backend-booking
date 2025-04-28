package NCT.com.Booking.DTO.Response;

import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token ;
    private boolean authenticated ;
}
