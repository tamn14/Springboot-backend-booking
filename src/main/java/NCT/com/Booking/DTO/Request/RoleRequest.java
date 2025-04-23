package NCT.com.Booking.DTO.Request;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private String name ;
    private List<Integer> users  ;
    private List<Integer> permissions  ;
}
