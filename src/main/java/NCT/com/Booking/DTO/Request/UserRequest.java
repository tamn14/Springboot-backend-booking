package NCT.com.Booking.DTO.Request;

import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(min = 4, message = "USERNAME_INVALID")
    private String userName ;
    @Size(min = 6, message = "INVALID_PASSWORD")
    private String passWord ;
    @NotBlank
    private String lastName ;
    @NotBlank
    private String firstName ;
    private List<Integer> roles  ;
    private List<Integer> bookings  ;
}
