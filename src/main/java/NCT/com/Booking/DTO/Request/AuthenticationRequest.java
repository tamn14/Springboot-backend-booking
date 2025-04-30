package NCT.com.Booking.DTO.Request;

import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AuthenticationRequest {
    private String username ;
    private String password ;
}
