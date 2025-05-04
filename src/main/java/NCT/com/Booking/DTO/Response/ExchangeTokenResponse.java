package NCT.com.Booking.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeTokenResponse {
    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String scope;
    private String tokenType;
}
