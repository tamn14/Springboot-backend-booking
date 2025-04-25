package NCT.com.Booking.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesRequest {
    private String name ;
    private List<Integer> users  ;
    private List<Integer> permissions  ;
}
