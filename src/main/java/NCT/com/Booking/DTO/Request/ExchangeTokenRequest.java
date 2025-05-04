package NCT.com.Booking.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeTokenRequest {
    private String code;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;

}
