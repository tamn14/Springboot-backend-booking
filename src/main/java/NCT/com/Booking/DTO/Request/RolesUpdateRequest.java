package NCT.com.Booking.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesUpdateRequest {
    private String name ;
    private Set<Integer> users  ;
    private Set<String> permissions  ;
}
