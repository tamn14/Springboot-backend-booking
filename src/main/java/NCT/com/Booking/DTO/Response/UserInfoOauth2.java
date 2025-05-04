package NCT.com.Booking.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoOauth2 {
    private String Id ;
    private String email ;
    private String verified_email ;
    private String name ;
    private String familyName ;
    private String picture ;
    private String locale ;

}
