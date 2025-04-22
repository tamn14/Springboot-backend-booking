package NCT.com.Booking.DTO.Response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {

    private String name ;
    private List<Integer> roles ;
}
