package NCT.com.Booking.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UsersUpdateRequest {

    @Size(min = 6, message = "INVALID_PASSWORD")
    private String passWord ;
    @NotBlank
    private String lastName ;
    @NotBlank
    private String firstName ;
    @NotBlank
    private String email ;
    private Set<String> roles;

}
