package NCT.com.Booking.DTO.Response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private String name ;
    private List<Integer> users  ;
    private List<Integer> permissions  ;
}
