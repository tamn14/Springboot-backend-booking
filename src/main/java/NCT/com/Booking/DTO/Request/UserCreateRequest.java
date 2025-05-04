package NCT.com.Booking.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    @Size(min = 4, message = "USERNAME_INVALID")
    private String userName ;
    @Size(min = 6, message = "INVALID_PASSWORD")
    private String passWord ;

    private String lastName ;

    private String firstName ;

    private String email ;
    @NonNull
    private Set<String> roles;
}
