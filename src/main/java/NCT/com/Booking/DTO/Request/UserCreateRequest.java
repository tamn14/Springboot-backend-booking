package NCT.com.Booking.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @Size(min = 4, message = "USERNAME_INVALID")
    private String userName ;
    @Size(min = 6, message = "INVALID_PASSWORD")
    private String passWord ;
    @NotBlank
    private String lastName ;
    @NotBlank
    private String firstName ;
}
