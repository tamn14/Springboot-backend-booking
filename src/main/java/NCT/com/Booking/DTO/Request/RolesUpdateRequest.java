package NCT.com.Booking.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolesUpdateRequest {
    private String name ;
    private Set<Integer> users  ;
    private Set<String> permissions  ;
}
