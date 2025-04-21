package NCT.com.Booking.DTO.Request;

import NCT.com.Booking.Entity.Booking;
import NCT.com.Booking.Entity.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String userName ;
    private String passWord ;
    private String lastName ;
    private String firstName ;
    private List<Integer> roles  ;
    private List<Integer> bookings  ;
}
