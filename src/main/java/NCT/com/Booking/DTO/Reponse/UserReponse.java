package NCT.com.Booking.DTO.Reponse;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReponse {
    private String userName ;
    private String passWord ;
    private String lastName ;
    private String firstName ;
    private List<Integer> roles  ;
    private List<Integer> bookings  ;
}
